package wolfdesk.coupon.infra

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import wolfdesk.coupon.domain.CouponCountRepository

@Component
class RedisCouponCountRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) : CouponCountRepository {

    override fun increment(key: String): Long {
        return redisTemplate
            .opsForValue()
            .increment("coupon_count") ?: 0L
    }
}
