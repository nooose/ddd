package wolfdesk.base.security.filter.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import wolfdesk.base.security.CustomAuthenticationToken
import wolfdesk.base.security.SecurityConfig.Companion.PERMIT_ALL_PATTERNS
import wolfdesk.base.support.bearerToken

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bearerToken = request.bearerToken()
        if (bearerToken.isNullOrBlank()) {
            val unauthenticatedToken = CustomAuthenticationToken.unauthenticated()
            SecurityContextHolder.getContext().authentication = unauthenticatedToken
            filterChain.doFilter(request, response)
            return
        }

        val authenticationToken = authenticateToken(bearerToken = bearerToken)
        SecurityContextHolder.getContext().authentication = authenticationToken
        filterChain.doFilter(request, response)
    }

    private fun authenticateToken(bearerToken: String): CustomAuthenticationToken {
        val member = jwtProvider.extractMemberPrincipal(bearerToken)
        return CustomAuthenticationToken.authenticated(member)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return PERMIT_ALL_PATTERNS.matches(request)
    }
}
