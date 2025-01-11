package wolfdesk.base.limit

/**
 * 사용자가 요청한 API
 */
data class UserApiRequest(
    val memberId: Long,
    val api: String,
)
