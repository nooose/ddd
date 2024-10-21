package wolfdesk.base.common.exception

import wolfdesk.base.common.exception.JwtProviderException.JwtErrorType.SIGNATURE_INVALID
import wolfdesk.base.common.exception.JwtProviderException.JwtErrorType.TOKEN_EXPIRED

data class JwtProviderException(
    val type: JwtErrorType?,
    override val message: String
) : Exception(message) {

    enum class JwtErrorType {
        SIGNATURE_INVALID,
        TOKEN_EXPIRED,
    }

    companion object {
        val INVALID_SIGNATURE: JwtProviderException = JwtProviderException(
            type = SIGNATURE_INVALID,
            message = "서명이 올바르지않습니다."
        )

        val EXPIRED: JwtProviderException = JwtProviderException(
            type = TOKEN_EXPIRED,
            message = "토큰이 만료되었습니다"
        )
    }
}
