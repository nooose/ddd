package wolfdesk.coupon.application

import org.springframework.stereotype.Service
import wolfdesk.coupon.domain.Coupon
import wolfdesk.coupon.domain.CouponCountRepository
import wolfdesk.coupon.domain.CouponRepository

@Service
class ApplyService(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository,
) {

    fun apply(tenantId: Long) {
        val count = couponCountRepository.increment("coupon")
        check(count < 10) { "응모가 종료되었습니다." }
        couponRepository.save(Coupon(tenantId = tenantId))
    }
}
