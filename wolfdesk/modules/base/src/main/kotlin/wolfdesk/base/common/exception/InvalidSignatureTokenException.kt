package wolfdesk.base.common.exception

import org.springframework.security.core.AuthenticationException

data class InvalidSignatureTokenException(
    override val message: String,
) : AuthenticationException(message)
