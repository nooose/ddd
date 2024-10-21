package wolfdesk.base.api

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val data: T?,
    val message: String,
) {

    companion object {
        fun <T : Any> success(data: T? = null): ApiResponse<T> {
            return ApiResponse(data, "SUCCESS")
        }
    }
}
