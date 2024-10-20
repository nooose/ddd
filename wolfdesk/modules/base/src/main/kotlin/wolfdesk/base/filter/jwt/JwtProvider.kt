package wolfdesk.base.filter.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
) {

    /**
     * 토큰 생성
     */
    fun generateToken(memberId: String, memberName: String, currentDate: LocalDateTime): String {
        return Jwts.builder()
            .subject(memberId)
            .claim("memberName", memberName)
            .issuedAt(Date())
            .expiration(expirationTime(currentDate))
            .signWith(getSigningKey())
            .compact()
    }

    private fun expirationTime(currentDate: LocalDateTime): Date {
        val expiration = Date(
            Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant()).time + jwtProperties.expiredTime
        )
        return expiration
    }

    /**
     * 토큰을 통해 회원 ID 추출
     */
    fun getMemberId(token: String): String {
        return extractClaims(token).subject
    }

    /**
     * 유효하지 않은 토큰인지 검증
     */
    fun validate(token: String) {
        extractClaims(token)
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
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
