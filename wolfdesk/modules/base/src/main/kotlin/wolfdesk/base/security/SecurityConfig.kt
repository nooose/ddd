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

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }

            authorizeHttpRequests {
                authorize("/login", permitAll)
                authorize("/register", permitAll)
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
