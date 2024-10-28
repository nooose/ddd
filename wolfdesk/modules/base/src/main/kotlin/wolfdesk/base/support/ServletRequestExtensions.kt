package wolfdesk.base.support

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders


private const val BEARER_PREFIX = "Bearer "

fun HttpServletRequest.bearerToken(): String? {
    val bearerToken = this.getHeader(HttpHeaders.AUTHORIZATION)
    return if (bearerToken.startsWith(BEARER_PREFIX)) {
        bearerToken.removePrefix(BEARER_PREFIX)
    } else null
}
