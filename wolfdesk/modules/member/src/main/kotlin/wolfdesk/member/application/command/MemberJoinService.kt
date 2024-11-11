package wolfdesk.member.application.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.member.domain.Member
import wolfdesk.member.domain.MemberRepository

@Transactional
@Service
class MemberJoinService(
    private val memberRepository: MemberRepository
) {

    fun register(command: MemberJoinCommand) {
        memberRepository.findByEmail(command.email)?.let {
            throw IllegalStateException("${command.email} 이메일은 이미 존재합니다.")
        }

        val member = Member(
            email = command.email,
            password = command.password,
            name = command.name,
        )
        memberRepository.save(member)
    }
}
