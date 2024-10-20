package wolfdesk.base.filter.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Duration
import java.time.LocalDateTime

class JwtProviderTest{
    private val memberId = "sim512@naver.com"
    private val memberName = "김영철"
    private val secretKey = "aaoisfjwaeoifnaweovnsaivonsadiovsahehrfoiwefnsdailfnsdailfsadnlfsa"
    private val expiredDuration = Duration.ofSeconds(2)

    @Test
    fun `JWT생성테스트`() {
        val jwtProvider = jwtProvider()

        val token = jwtProvider.generateToken(
            memberId = memberId,
            memberName = memberName,
            LocalDateTime.now()
        )

        assertThat(token).isNotNull()
        assertThat(jwtProvider.getMemberId(token)).isEqualTo(memberId)

    }

    @Test
    fun `JWT 유효성 테스트`() {
        val validJwtProvider = jwtProvider()

        val invalidJwtProvider = JwtProvider(
            JwtProperties(
                secretKey = "ncvbmcbldghfsoifawjoifwaenoifwanoifawdfnsdoifnasdoifnawfoiawenifnwa",
                expiredDuration = expiredDuration,
            )
        )

        val token = invalidJwtProvider.generateToken(
            memberId = memberId,
            memberName = memberName,
            currentDate = LocalDateTime.now()
        )

        assertThrows<SignatureException> { validJwtProvider.validate(token) }
    }

    @Test
    fun `JWT 만료 테스트`() {
        val validJwtProvider = jwtProvider()

        val now = LocalDateTime.now()
        val yesterday = now.minusDays(1)

        val expiredToken = validJwtProvider.generateToken(
            memberId = memberId,
            memberName = memberName,
            yesterday,
        )

        assertThrows<ExpiredJwtException> { validJwtProvider.validate(expiredToken) }
    }

    private fun jwtProvider(): JwtProvider {
        return JwtProvider(
            JwtProperties(
                secretKey = secretKey,
                expiredDuration = Duration.ofSeconds(2),
            )
        )
    }
}
