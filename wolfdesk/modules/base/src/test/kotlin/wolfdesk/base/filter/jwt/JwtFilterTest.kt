package wolfdesk.base.filter.jwt

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import jakarta.servlet.FilterChain
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import wolfdesk.base.common.exception.ExpiredTokenException
import wolfdesk.base.common.exception.InvalidSignatureTokenException
import wolfdesk.base.security.MemberPrincipal
import wolfdesk.base.security.filter.jwt.JwtFilter
import java.time.LocalDateTime

class JwtFilterTest : StringSpec({
    val validJwtProvider = jwtProviderFixture()
    val invalidJwtProvider = jwtProviderFixture(secretKey = "B".repeat(64))
    val jwtFilter = JwtFilter(
        jwtProvider = validJwtProvider,
    )

    val filterChain = mockk<FilterChain>(relaxed = true)
    val response = MockHttpServletResponse()

    val memberPrincipal = MemberPrincipal(memberId = 1L)
    val shouldFilterURI = "/members/me"

    beforeEach {
        SecurityContextHolder.getContext().authentication = null
    }

    "JWT 서명이 올바르지 않을 경우 예외가 발생한다" {
        val invalidToken = invalidJwtProvider.generateToken(memberPrincipal = memberPrincipal)
        val request = mockRequest(HttpMethod.POST, invalidToken.accessToken, shouldFilterURI)

        shouldThrow<InvalidSignatureTokenException> {
            jwtFilter.doFilter(request, response, filterChain)
        }
    }

    "JWT가 만료되었을 경우 예외가 발생한다" {
        val expiredToken = validJwtProvider.generateToken(
            memberPrincipal = memberPrincipal,
            now = LocalDateTime.now().minusDays(7),
        )
        val request = mockRequest(HttpMethod.POST, expiredToken.accessToken, shouldFilterURI)

        shouldThrow<ExpiredTokenException> {
            jwtFilter.doFilter(request, response, filterChain)
        }
    }

    "JWT가 유효할 경우 사용자 인증이 이루어진다" {
        val validToken = validJwtProvider.generateToken(memberPrincipal = memberPrincipal)
        val request = mockRequest(HttpMethod.POST, validToken.accessToken, shouldFilterURI)

        jwtFilter.doFilter(request, response, filterChain)

        val authentication = SecurityContextHolder.getContext().authentication
        val principal: MemberPrincipal = authentication.principal as MemberPrincipal

        authentication shouldNotBe null
        authentication.isAuthenticated.shouldBeTrue()
        principal.memberId shouldBe memberPrincipal.memberId
        authentication.authorities shouldBe emptyList()
    }

    "헤더에 JWT가 없다면 인증되지 않는다" {
        val request = mockRequest(HttpMethod.POST, null, shouldFilterURI)
        jwtFilter.doFilter(request, response, filterChain)

        val authentication = SecurityContextHolder.getContext().authentication

        authentication.isAuthenticated.shouldBeFalse()
    }
})

private fun mockRequest(method: HttpMethod, token: String? = null, url: String): MockHttpServletRequest {
    return MockHttpServletRequest().apply {
        this.method = method.name()
        requestURI = url
        token?.let {
            addHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
    }
}
