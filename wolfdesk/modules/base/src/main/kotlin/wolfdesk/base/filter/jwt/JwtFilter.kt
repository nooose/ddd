package wolfdesk.base.filter.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import wolfdesk.base.security.SecurityConfig
import wolfdesk.base.support.bearerToken

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response)
            return
        }

        val token = request.bearerToken()
        if (!isToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패 - 토큰이 없습니다")
            return
        }

        authenticateUser(token!!, request)
        filterChain.doFilter(request, response)
    }

    private fun isToken(token: String?) = token.isNullOrBlank()

    private fun authenticateUser(token: String, request: HttpServletRequest) {
        val memberId = jwtProvider.extractMemberId(token)

        if (SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(memberId.toString())
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )

            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }

    private fun shouldSkipFilter(request: HttpServletRequest) =
        request.requestURI in SecurityConfig.PERMITALL_URIS
}

