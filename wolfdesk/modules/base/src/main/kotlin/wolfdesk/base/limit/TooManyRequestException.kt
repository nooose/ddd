package wolfdesk.base.limit

data class TooManyRequestException(
    override val message: String
) : Exception(message)
