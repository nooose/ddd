package wolfdesk.base.filter.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component
import wolfdesk.base.common.exception.JwtProviderException
import wolfdesk.base.support.toDate
import java.time.LocalDateTime
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtProvider(
    private val properties: JwtProperties,
) {

    /**
     * 토큰 생성
     */
    fun generateToken(
        memberId: Long,
        now: LocalDateTime = LocalDateTime.now()
    ): String {
        val nowDate = now.toDate()
        return Jwts.builder()
            .subject(memberId.toString())
            .issuedAt(nowDate)
            .expiration(expirationDate(nowDate))
            .signWith(getSigningKey())
            .compact()
    }

    private fun expirationDate(date: Date): Date {
        val nowDate = date.time
        val expired = properties.expired.toMillis()
        return Date(nowDate + expired)
    }

    /**
     * 토큰을 통해 회원 ID 추출
     */
    fun extractMemberId(token: String): Long {
        return try {
            extractClaims(token).subject.toLong()
        } catch (e: JwtException) {
            throw e.convert()
        }
    }

    private fun JwtException.convert(): JwtProviderException {
        return when (this) {
            is ExpiredJwtException -> JwtProviderException.EXPIRED
            is SignatureException -> JwtProviderException.INVALID_SIGNATURE
            else -> JwtProviderException(null, "Invalid token")
        }
    }

    // 토큰 claims 정보 추출
    private fun extractClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(properties.secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
