package wolfdesk.base.filter.jwt

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.DispatcherType
import jakarta.servlet.FilterChain
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import wolfdesk.base.common.exception.ExpiredTokenException
import wolfdesk.base.common.exception.InvalidSignatureTokenException
import java.time.LocalDateTime

class JwtFilterTest : StringSpec({
    val validJwtProvider = jwtProviderFixture()
    val invalidJwtProvider = jwtProviderFixture(secretKey = "B".repeat(64))
    val userDetailsService = mockk<UserDetailsService>()
    val filter = JwtFilter(
        jwtProvider = validJwtProvider,
        userDetailsService = userDetailsService,
    )

    val filterChain = mockk<FilterChain>(relaxed = true)
    val response = MockHttpServletResponse()

    val memberId = 1L
    val userDetails = mockk<UserDetails>()
    val defaultRequestURI = "/members/info"

    beforeEach {
        every { userDetailsService.loadUserByUsername(memberId.toString()) } returns userDetails
        every { userDetails.authorities } returns emptyList()
    }

    fun mockRequest(token: String): MockHttpServletRequest {
        return MockHttpServletRequest().apply {
            dispatcherType = DispatcherType.REQUEST
            requestURI = defaultRequestURI
            addHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")
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
        every { userDetails.username } returns memberId.toString()

        filter.doFilter(
            request,
            response,
            filterChain,
        )

        val authentication = SecurityContextHolder.getContext().authentication
        authentication shouldNotBe null
        authentication.name shouldBe memberId.toString()
        authentication.authorities shouldBe emptyList()
    }
})
