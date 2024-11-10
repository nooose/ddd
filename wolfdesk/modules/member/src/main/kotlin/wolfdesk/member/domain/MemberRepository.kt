package wolfdesk.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByEmailIs(email: String): Boolean
    fun findByEmail(email: String): Member?
}
