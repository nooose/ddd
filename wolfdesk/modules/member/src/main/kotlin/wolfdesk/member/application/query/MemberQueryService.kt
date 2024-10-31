package wolfdesk.member.application.query

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import wolfdesk.base.filter.jwt.JwtProvider
import wolfdesk.base.filter.jwt.MemberPrincipal
import wolfdesk.member.domain.Member
import wolfdesk.member.domain.MemberRepository

@Service
class MemberQueryService(
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider,
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    fun login(query: MemberQuery): String {
        val member = validateMember(query)
        val memberPrincipal = MemberPrincipal(memberId = member.id)
        return jwtProvider.generateToken(memberPrincipal = memberPrincipal)
    }

    private fun validateMember(query: MemberQuery): Member {
        val member = memberRepository.findByEmail(query.email)
            ?: throw IllegalStateException("로그인 실패: 사용자 정보를 찾을 수 없습니다. - ${query.email}")

        if (!passwordEncoder.matches(member.password, query.password)) {
            throw BadCredentialsException("로그인 실패")
        }
        return member
    }

    fun getMember(id: Long): MemberInfo {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw IllegalStateException("멤버를 찾을 수 없습니다 - $id")
        return MemberInfo(
            name = member.name
        )
    }
}
