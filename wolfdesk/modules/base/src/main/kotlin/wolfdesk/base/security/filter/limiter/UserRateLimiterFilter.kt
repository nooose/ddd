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
import wolfdesk.base.limit.UserApiRateLimiter
import wolfdesk.base.limit.TooManyRequestException
import wolfdesk.base.limit.UserApiRequest
import wolfdesk.base.security.MemberPrincipal

@Component
class UserRateLimiterFilter(
    private val userApiRateLimiter: UserApiRateLimiter,
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
            val apiRequest = UserApiRequest(principal.memberId, request.requestURI)
            userApiRateLimiter.acquire(apiRequest)
        } catch (e: TooManyRequestException) {
            response.errorResponse(e)
            return
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.method == HttpMethod.GET.name()
    }

    private fun HttpServletResponse.errorResponse(e: TooManyRequestException) {
        val errorResponse = ApiResponse.error(e.message)
        val errorJson = objectMapper.writeValueAsString(errorResponse)
        this.contentType = MediaType.APPLICATION_JSON_VALUE
        this.status = HttpStatus.TOO_MANY_REQUESTS.value()
        this.characterEncoding = "UTF-8"
        this.writer.write(errorJson)
    }
}
