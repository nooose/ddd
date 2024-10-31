package wolfdesk.base.support

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import wolfdesk.base.common.exception.CustomAuthenticationException


private const val BEARER_PREFIX = "Bearer "

fun HttpServletRequest.bearerToken(): String {
    val bearerToken = this.getHeader(HttpHeaders.AUTHORIZATION)

    if (isNotToken(bearerToken)) {
        throw CustomAuthenticationException("토큰이 없습니다")
    }

    return bearerToken.removePrefix(BEARER_PREFIX)
}

private fun isNotToken(bearerToken: String?) = bearerToken.isNullOrBlank() || !bearerToken.startsWith(BEARER_PREFIX)
