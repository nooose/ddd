package wolfdesk.base.common.exception

import org.springframework.security.core.AuthenticationException

data class ExpiredTokenException(
    override val message: String,
) : AuthenticationException(message)
