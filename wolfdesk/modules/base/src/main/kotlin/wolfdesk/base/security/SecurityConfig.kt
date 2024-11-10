package wolfdesk.base.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher
import org.springframework.security.web.util.matcher.RequestMatchers.anyOf
import wolfdesk.base.security.filter.jwt.JwtFilter

@Configuration
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }

            authorizeHttpRequests {
                authorize(PERMIT_ALL_PATTERNS, permitAll)
                authorize(anyRequest, authenticated)
            }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            exceptionHandling {
                authenticationEntryPoint = customAuthenticationEntryPoint
            }

            formLogin { disable() }
            addFilterAfter<ExceptionTranslationFilter>(jwtFilter)
        }

        return http.build()
    }

    companion object {
        val PERMIT_ALL_PATTERNS = anyOf(
            antMatcher(HttpMethod.POST, "/members"),
            antMatcher(HttpMethod.POST, "/auth/token"),
        )!!
    }
}
