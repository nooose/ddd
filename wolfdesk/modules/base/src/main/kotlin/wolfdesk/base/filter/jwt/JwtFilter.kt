package wolfdesk.base.filter.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
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
        authenticateUser(
            token = request.bearerToken(),
            request = request,
        )

        filterChain.doFilter(request, response)
    }

    private fun authenticateUser(token: String, request: HttpServletRequest) {
        // TODO: 향후 권한이 추가되면 Token 에서 꺼내서 CustomAuthenticationToken 의 인자값으로 권한리스트도 넣어줘야 함
        val member = jwtProvider.extractMemberPrincipal(token)
        val authentication = CustomAuthenticationToken(member)
        SecurityContextHolder.getContext().authentication = authentication
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.requestURI in SecurityConfig.PERMIT_ALL_URIS
    }
}

