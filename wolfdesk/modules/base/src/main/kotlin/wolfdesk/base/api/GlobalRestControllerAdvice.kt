package wolfdesk.base.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun error(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        val response = ApiResponse.error(e.message ?: "")
        return ResponseEntity.badRequest().body(response)
    }
}
