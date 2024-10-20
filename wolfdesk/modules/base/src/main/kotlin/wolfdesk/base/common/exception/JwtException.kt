package wolfdesk.base.common.exception

class JwtException(
    val errorType: JwtErrorType,
    override val message: String
) : RuntimeException(message)

enum class JwtErrorType {
    SIGNATURE_INVALID,
    TOKEN_EXPIRED,
}
