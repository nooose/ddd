package wolfdesk.base.security.filter.limiter

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import wolfdesk.base.limit.RateLimiter
import wolfdesk.base.limit.TooManyRequestException

class RateLimiterFilterTest : StringSpec({

    "빠른시간에 같은 요청이 들어오면 예외를 던진다." {
        val limiter = RateLimiter()

        limiter.acquire(1L)
        shouldThrow<TooManyRequestException> {
            limiter.acquire(1L)
        }
    }

    "아이디가 다르면 같은 요청으로 취급하지 않는다." {
        val limiter = RateLimiter()
        limiter.acquire(1L)
        shouldNotThrowAny { limiter.acquire(2L) }
    }
})
