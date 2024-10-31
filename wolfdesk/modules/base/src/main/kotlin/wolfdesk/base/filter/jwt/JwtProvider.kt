package wolfdesk.base.filter.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component
import wolfdesk.base.common.exception.ExpiredTokenException
import wolfdesk.base.common.exception.InvalidSignatureTokenException
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
     * TODO: 향후 권한이 추가되면 memberId + 권한리스트가 담긴 객체를 받아 토큰생성 시 권한도 claim 으로 추가해줘야한다
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
     * 토큰을 통해 회원 정보 추출
     */
    fun extractMemberPrincipal(token: String): MemberPrincipal {
        return try {
            // TODO: 나중에 권한 추가되면 권한도 같이 넣어야함
            MemberPrincipal(memberId = extractClaims(token).subject.toLong())
        } catch (e: JwtException) {
            throw e.convert()
        }
    }

    private fun JwtException.convert(): Exception {
        return when (this) {
            is ExpiredJwtException -> ExpiredTokenException("토큰이 만료되었습니다")
            is SignatureException -> InvalidSignatureTokenException("서명이 올바르지않습니다.")
            else -> IllegalStateException("Invalid token")
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
