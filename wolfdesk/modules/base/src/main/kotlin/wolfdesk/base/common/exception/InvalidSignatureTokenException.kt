package wolfdesk.base.common.exception

data class InvalidSignatureTokenException(
    override val message: String,
) : Exception(message)
