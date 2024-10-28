package wolfdesk.base.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import wolfdesk.base.filter.jwt.JwtFilter

@Configuration
class SecurityConfig(
    private val jwtFilter: JwtFilter,
) {
    companion object {
        val PERMITALL_URIS = setOf(
            "/members/login",
            "/members/register",
        )
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }

            authorizeHttpRequests {
                PERMITALL_URIS.forEach {
                    authorize(it, permitAll)
                }
                authorize(anyRequest, authenticated)
            }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            httpBasic { }
            formLogin { disable() }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtFilter)
        }

        return http.build()
    }
}
