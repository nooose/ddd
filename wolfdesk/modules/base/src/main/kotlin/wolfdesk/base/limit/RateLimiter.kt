package wolfdesk.base.limit

import io.github.bucket4j.BandwidthBuilder
import io.github.bucket4j.Bucket
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Service
class RateLimiter {

    private val _buckets: MutableMap<Long, Bucket> = ConcurrentHashMap()

    fun acquire(id: Long) {
        val bucket = this._buckets.computeIfAbsent(id) { createNewBucket() }
        val tryConsume = bucket.tryConsume(1)
        if (!tryConsume) {
            throw TooManyRequestException("허용된 요청 수를 초과했습니다.")
        }
    }

    private fun createNewBucket(): Bucket {
        val bandwidth = BandwidthBuilder.builder()
            .capacity(1)
            .refillIntervally(1, Duration.ofSeconds(1))
            .build()
        return Bucket.builder()
            .addLimit(bandwidth)
            .build()
    }
}
