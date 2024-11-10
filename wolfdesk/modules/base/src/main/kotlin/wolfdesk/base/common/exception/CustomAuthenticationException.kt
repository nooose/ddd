package wolfdesk.base.common.exception

import org.springframework.security.core.AuthenticationException

data class CustomAuthenticationException(
    override val message: String?,
) : AuthenticationException(message)
