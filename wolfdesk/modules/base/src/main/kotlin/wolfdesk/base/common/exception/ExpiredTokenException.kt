package wolfdesk.base.common.exception

data class ExpiredTokenException(
    override val message: String
) : Exception(message)
