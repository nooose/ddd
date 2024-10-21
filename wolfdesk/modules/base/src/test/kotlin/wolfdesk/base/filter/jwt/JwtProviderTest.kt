package wolfdesk.base.filter.jwt

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import wolfdesk.base.common.exception.JwtProviderException
import wolfdesk.base.common.exception.JwtProviderException.JwtErrorType.SIGNATURE_INVALID
import wolfdesk.base.common.exception.JwtProviderException.JwtErrorType.TOKEN_EXPIRED
import java.time.Duration
import java.time.LocalDateTime

class JwtProviderTest {

    @Test
    fun `JWT 생성테스트`() {
        val provider = jwtProviderFixture()
        val token = provider.generateToken(memberId = 1L)

        assertThat(token).isNotNull()
        assertThat(provider.extractMemberId(token)).isEqualTo(1L)

    }

    @Test
    fun `JWT 서명 테스트`() {
        val validJwtProvider = jwtProviderFixture()
        val invalidJwtProvider = jwtProviderFixture(secretKey = "B".repeat(64))

        val invalidToken = invalidJwtProvider.generateToken(memberId = 1L)

        val exception = assertThrows<JwtProviderException> {
            validJwtProvider.extractMemberId(invalidToken)
        }

        assertThat(exception.type).isEqualTo(SIGNATURE_INVALID)
    }

    @Test
    fun `JWT 만료 테스트`() {
        val validJwtProvider = jwtProviderFixture()
        val expiredToken = validJwtProvider.generateToken(
            memberId = 1L,
            now = LocalDateTime.now().minusDays(7),
        )

        val exception = assertThrows<JwtProviderException> {
            validJwtProvider.extractMemberId(expiredToken)
        }

        assertThat(exception.type).isEqualTo(TOKEN_EXPIRED)

    }
}

fun jwtProviderFixture(
    secretKey: String = "A".repeat(64),
    duration: Duration = Duration.ofMinutes(10)
): JwtProvider {
    return JwtProvider(
        properties = JwtProperties(
            secretKey = secretKey,
            expired = duration,
        )
    )
}
