package wolfdesk.base.filter.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    val expiredDuration: Duration,
) {
    val expiredTime = expiredDuration.toMillis()
}
