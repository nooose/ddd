package wolfdesk.member.application.command

import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import wolfdesk.member.domain.Member
import wolfdesk.member.domain.MemberRepository

@Service
class MemberCommandService(
    private val memberRepository: MemberRepository
) {

    fun register(command: MemberCommand) {
        // todo: 이 암호화하는 부분을 이렇게 그냥 서비스에서 하는게 맞는지 잘 모르겠음
        // command 객체에서 꺼내면서 암호화하는게 낫나?
        check(memberRepository.existsByEmailIs(command.email)) {
            "${command.email} - 이미 존재하는 이메일입니다."
        }

        val encryptedPassword = BCrypt.hashpw(command.password, BCrypt.gensalt())
        val member = Member(
            email = command.email,
            password = encryptedPassword,
            name = command.name,
        )
        memberRepository.save(member)
    }
}
