package wolfdesk.base.filter.jwt

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import wolfdesk.base.common.exception.ExpiredTokenException
import wolfdesk.base.common.exception.InvalidSignatureTokenException
import wolfdesk.base.security.MemberPrincipal
import wolfdesk.base.security.filter.jwt.JwtProperties
import wolfdesk.base.security.filter.jwt.JwtProvider
import java.time.Duration
import java.time.LocalDateTime

class JwtProviderTest : StringSpec({

    val memberPrincipal = MemberPrincipal(1L)

    "jwt 를 생성하고 payload 를 읽어볼 수 있다" {
        val provider = jwtProviderFixture()
        val token = provider.generateToken(memberPrincipal = memberPrincipal)

        token shouldNotBe null
        provider.extractMemberPrincipal(token).memberId shouldBe 1L
    }

    "jwt 서명이 유효하지 않으면 예외를 던진다" {
        val validJwtProvider = jwtProviderFixture()
        val invalidJwtProvider = jwtProviderFixture(secretKey = "B".repeat(64))
        val invalidToken = invalidJwtProvider.generateToken(memberPrincipal = memberPrincipal)

        shouldThrow<InvalidSignatureTokenException> {
            validJwtProvider.extractMemberPrincipal(invalidToken)
        }
    }

    "jwt 의 유효기간이 만료되면 예외가 던진다" {
        val validJwtProvider = jwtProviderFixture()
        val expiredToken = validJwtProvider.generateToken(
            memberPrincipal = memberPrincipal,
            now = LocalDateTime.now().minusDays(7),
        )

        shouldThrow<ExpiredTokenException> {
            validJwtProvider.extractMemberPrincipal(expiredToken)
        }
    }
})

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
