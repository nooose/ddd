package wolfdesk.coupon.domain

interface CouponCountRepository {

    fun increment(key: String): Long
}
