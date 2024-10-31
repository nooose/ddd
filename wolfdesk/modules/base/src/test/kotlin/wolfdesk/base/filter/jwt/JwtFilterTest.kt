package wolfdesk.base.filter.jwt

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import jakarta.servlet.DispatcherType
import jakarta.servlet.FilterChain
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import wolfdesk.base.common.exception.ExpiredTokenException
import wolfdesk.base.common.exception.InvalidSignatureTokenException
import java.time.LocalDateTime

class JwtFilterTest : StringSpec({
    val validJwtProvider = jwtProviderFixture()
    val invalidJwtProvider = jwtProviderFixture(secretKey = "B".repeat(64))
    val filter = JwtFilter(
        jwtProvider = validJwtProvider,
    )

    val filterChain = mockk<FilterChain>(relaxed = true)
    val response = MockHttpServletResponse()

    val memberId = 1L
    val shouldFilterURI = "/members/info"
    val shouldNotFilterURI = "/members/login"

    // TODO: StringSpec 안에서 동작하는 테스트 케이스들이 SecurityContextHolder.getContext().authentication 를 공유해서
    // 각각 테스트 실행전마다 초기화시켜주기위해 작성
    beforeEach { SecurityContextHolder.getContext().authentication = null }

    fun mockRequest(token: String? = null, url: String = shouldFilterURI): MockHttpServletRequest {
        return MockHttpServletRequest().apply {
            dispatcherType = DispatcherType.REQUEST
            requestURI = url
            if (token != null) {
                addHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
            remoteAddr = "127.0.0.1"
        }
    }

    "요청과 함께 온 token의 서명이 올바르지 않을 경우 예외가 발생한다" {
        val invalidToken = invalidJwtProvider.generateToken(memberId = memberId)
        val request = mockRequest(invalidToken)

        shouldThrow<InvalidSignatureTokenException> {
            filter.doFilter(request, response, filterChain)
        }
    }

    "요청과 함께 온 token이 만료되었을 경우 예외가 발생한다" {
        val expiredToken = validJwtProvider.generateToken(
            memberId = memberId,
            now = LocalDateTime.now().minusDays(7),
        )
        val request = mockRequest(expiredToken)

        shouldThrow<ExpiredTokenException> {
            filter.doFilter(request, response, filterChain)
        }
    }

    "요청과 함께 온 token이 유효할 경우 사용자 인증이 이루어진다" {
        val validToken = validJwtProvider.generateToken(memberId = memberId)
        val request = mockRequest(validToken)

        filter.doFilter(
            request,
            response,
            filterChain,
        )

        val authentication = SecurityContextHolder.getContext().authentication
        val principal: MemberPrincipal = authentication.principal as MemberPrincipal

        authentication shouldNotBe null
        principal.memberId shouldBe memberId
        authentication.authorities shouldBe emptyList()
    }

    "api 요청 주소가 PERMIT_ALL_URIS 인 경우 authentication 는 null 이다" {
        val validToken = validJwtProvider.generateToken(memberId = memberId)
        val request = mockRequest(
            token = validToken,
            url = shouldNotFilterURI
        )

        filter.doFilter(
            request,
            response,
            filterChain,
        )

        val authentication = SecurityContextHolder.getContext().authentication
        authentication shouldBe null
    }

    "요청에 토큰이 없다면 예외가 발생한다" {
        val request = mockRequest(url = shouldFilterURI)

        shouldThrow<AuthenticationException> {
            filter.doFilter(
                request,
                response,
                filterChain,
            )
        }
    }
})
