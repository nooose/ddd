package wolfdesk.member.application.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.base.security.MemberPrincipal
import wolfdesk.base.security.TokenResponse
import wolfdesk.base.security.filter.jwt.JwtProvider
import wolfdesk.member.application.query.MemberLoginCommand
import wolfdesk.member.domain.Member
import wolfdesk.member.domain.MemberRepository

@Service
class MemberLoginService(
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider,
) {

    @Transactional(readOnly = true)
    fun login(query: MemberLoginCommand): TokenResponse {
        val member = validateMember(query)
        val memberPrincipal = MemberPrincipal(memberId = member.id)
        return jwtProvider.generateToken(memberPrincipal = memberPrincipal)
    }

    private fun validateMember(command: MemberLoginCommand): Member {
        val member = memberRepository.findByEmail(command.email)
            ?: throw IllegalStateException("회원 정보가 일치하지 않습니다.")
        // TODO: 커스텀 예외 사용하여 401 응답
        member.matches(command.password)
        return member
    }
}
