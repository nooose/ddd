package wolfdesk.base.filter.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthenticationToken(
    private val principal: MemberPrincipal,
    authorities: Collection<GrantedAuthority> = emptyList()
) : AbstractAuthenticationToken(authorities) {

    override fun getPrincipal() = principal
    override fun getCredentials() = null

    init {
        isAuthenticated = true
    }
}

data class MemberPrincipal(
    val memberId: Long,
)
