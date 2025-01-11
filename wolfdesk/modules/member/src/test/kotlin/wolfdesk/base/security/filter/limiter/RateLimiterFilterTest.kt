package wolfdesk.base.security.filter.limiter

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import wolfdesk.base.limit.UserApiRateLimiter
import wolfdesk.base.limit.TooManyRequestException
import wolfdesk.base.limit.UserApiRequest

class RateLimiterFilterTest : StringSpec({
    val request = UserApiRequest(1L, "/api")

    "빠른시간에 같은 요청이 들어오면 예외를 던진다." {
        val limiter = UserApiRateLimiter()

        limiter.acquire(request)
        shouldThrow<TooManyRequestException> {
            limiter.acquire(request)
        }
    }

    "요청자가 다르면 같은 요청으로 취급하지 않는다." {
        val limiter = UserApiRateLimiter()
        limiter.acquire(request)
        shouldNotThrowAny { limiter.acquire(request.copy(memberId = 2L)) }
    }
})
