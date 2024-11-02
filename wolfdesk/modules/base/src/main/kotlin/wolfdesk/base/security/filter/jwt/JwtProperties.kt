package wolfdesk.base.security.filter.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    val expired: Duration,
) {
    init {
        require(secretKey.length == 64) { "Secret key must be 64 - ${secretKey.length}" }
    }
}
