package wolfdesk.base.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthenticationToken(
    private val principal: MemberPrincipal?,
    authorities: Collection<GrantedAuthority> = emptyList()
) : AbstractAuthenticationToken(authorities) {

    override fun getPrincipal() = principal
    override fun getCredentials() = null

    companion object {
        fun authenticated(principal: MemberPrincipal): CustomAuthenticationToken {
            return CustomAuthenticationToken(principal).apply { isAuthenticated = true }
        }

        fun unauthenticated(): CustomAuthenticationToken {
            return CustomAuthenticationToken(null)
        }
    }
}

