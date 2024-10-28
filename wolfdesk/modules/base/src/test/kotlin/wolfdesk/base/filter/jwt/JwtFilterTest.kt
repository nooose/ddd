package wolfdesk.base.filter.jwt

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import jakarta.servlet.DispatcherType
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
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

    val request = mockk<HttpServletRequest>()
    val response = mockk<HttpServletResponse>()
    val filterChain = mockk<FilterChain>(relaxed = true)

    val memberId = 1L
    val userDetails = mockk<UserDetails>()

    beforeEach {
        every { userDetailsService.loadUserByUsername(memberId.toString()) } returns userDetails
        every { userDetails.authorities } returns emptyList()
        every { request.getAttribute("wolfdesk.base.filter.jwt.JwtFilter.FILTERED") } returns null
        every { request.getAttribute("jakarta.servlet.error.request_uri") } returns null
        every { request.setAttribute(any(), any()) } just Runs
        every { request.removeAttribute(any()) } just Runs
        every { request.dispatcherType } returns DispatcherType.REQUEST
        every { request.requestURI } returns "/members/info"
    }

    "요청과 함께 온 token의 서명이 올바르지 않을 경우 예외가 발생한다" {
        val invalidToken = invalidJwtProvider.generateToken(memberId = memberId)
        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "Bearer $invalidToken"

        shouldThrow<InvalidSignatureTokenException> {
            filter.doFilter(request, response, filterChain)
        }
    }

    "요청과 함께 온 token이 만료되었을 경우 예외가 발생한다" {
        val expiredToken = validJwtProvider.generateToken(
            memberId = memberId,
            now = LocalDateTime.now().minusDays(7),
        )
        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "Bearer $expiredToken"

        shouldThrow<ExpiredTokenException> {
            filter.doFilter(request, response, filterChain)
        }
    }
})
