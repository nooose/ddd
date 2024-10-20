import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import wolfdesk.base.common.exception.JwtErrorType.*
import wolfdesk.base.common.exception.JwtException
import wolfdesk.base.filter.jwt.JwtProvider

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractJwtFromRequest(request)
        if (token != null) {
            try {
                jwtProvider.validate(token)
                val memberId = jwtProvider.getMemberId(token)

                if (memberId.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService.loadUserByUsername(memberId)
                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )

                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            } catch (e: ExpiredJwtException) {
                JwtException(
                    errorType = TOKEN_EXPIRED,
                    message = "토큰이 만료되었습니다"
                )
            } catch (e: SignatureException) {
                JwtException(
                    errorType = SIGNATURE_INVALID,
                    message = "서명이 올바르지않습니다."
                )
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}
