package wolfdesk.base.support

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders


private const val BEARER_PREFIX = "Bearer "

fun HttpServletRequest.bearerToken(): String? {
    val bearerToken = this.getHeader(HttpHeaders.AUTHORIZATION)
    return bearerToken?.removePrefix(BEARER_PREFIX)
}
