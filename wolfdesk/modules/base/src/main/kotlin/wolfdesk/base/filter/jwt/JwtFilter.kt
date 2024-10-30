package wolfdesk.base.filter.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import wolfdesk.base.security.SecurityConfig
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
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response)
            return
        }

        authenticateUser(
            token = request.bearerToken()!!,
            request = request,
            response = response,
        )

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requestURI in SecurityConfig.PERMITALL_URIS
    }

    private fun authenticateUser(token: String, request: HttpServletRequest, response: HttpServletResponse) {
        if (isTokenInvalid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패 - 토큰이 없습니다")
            return
        }

        val memberId = jwtProvider.extractMemberId(token)

        if (SecurityContextHolder.getContext().authentication != null) {
            return
        }

        // TODO: 향후 권한이 추가되면 Token 에서 꺼내서 CustomAuthenticationToken 의 인자값으로 권한리스트도 넣어줘야 함
        val authentication = CustomAuthenticationToken(memberId)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun isTokenInvalid(token: String?) = token.isNullOrBlank()
}

