package wolfdesk.base.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import wolfdesk.base.api.ApiResponse

@Component
class CustomAuthenticationEntryPoint(
    private val mapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val exceptionMessage = authException.message ?: ""
        val errorResponse = ApiResponse.error<String>(exceptionMessage)
        val errorJson = mapper.writeValueAsString(errorResponse)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.writer.write(errorJson)
    }
}
