package wolfdesk.base.api

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val data: T?,
    val message: String,
    val isSuccess: Boolean,
) {

    companion object {
        fun success(): ApiResponse<Unit> {
            return ApiResponse(null, "SUCCESS", true)
        }

        fun <T : Any> success(data: T? = null): ApiResponse<T> {
            return ApiResponse(data, "SUCCESS", true)
        }

        fun error(message: String): ApiResponse<Unit> {
            return ApiResponse(null, message, false)
        }
    }
}
