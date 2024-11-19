package wolfdesk.coupon.ui.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import wolfdesk.coupon.application.ApplyService

@RestController
class CouponRestController(
    private val couponApplyService: ApplyService,
) {

    @PostMapping("/coupons")
    fun apply() {
        couponApplyService.apply(1)
    }
}
