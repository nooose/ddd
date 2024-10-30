package wolfdesk.base.filter.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthenticationToken(
    private val principal: Any,
    private val credentials: Any? = null,
    authorities: Collection<GrantedAuthority> = emptyList()
) : AbstractAuthenticationToken(authorities) {

    override fun getPrincipal() = principal
    override fun getCredentials() = credentials

    init {
        isAuthenticated = true
    }
}
