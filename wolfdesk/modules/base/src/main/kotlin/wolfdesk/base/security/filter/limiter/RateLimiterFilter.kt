package wolfdesk.base.security.filter.limiter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import wolfdesk.base.api.ApiResponse
import wolfdesk.base.limit.RateLimiter
import wolfdesk.base.limit.TooManyRequestException
import wolfdesk.base.security.MemberPrincipal

@Component
class RateLimiterFilter(
    private val rateLimiter: RateLimiter,
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = SecurityContextHolder.getContext().authentication
        val isAuthenticated = authentication.isAuthenticated
        if (!isAuthenticated || authentication is AnonymousAuthenticationToken) {
            filterChain.doFilter(request, response)
            return
        }

        val principal = authentication.principal as MemberPrincipal

        try {
            rateLimiter.acquire(principal.memberId)
        } catch (e: TooManyRequestException) {
            val errorResponse = ApiResponse.error("너무 많은 요청을 시도했습니다.")
            val errorJson = objectMapper.writeValueAsString(errorResponse)
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.characterEncoding = "UTF-8"
            response.writer.write(errorJson)
            return
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.method == HttpMethod.GET.name()
    }
}
