package wolfdesk.member.application.query

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import wolfdesk.member.domain.MemberRepository

@Service
class MemberQueryService(
    private val memberRepository: MemberRepository,
) {

    fun getMember(id: Long): MemberInfo {
        val member = memberRepository.findByIdOrNull(id)
            ?: throw IllegalStateException("멤버를 찾을 수 없습니다 - $id")
        return MemberInfo(
            name = member.name
        )
    }
}
