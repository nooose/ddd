package wolfdesk.member.domain

import jakarta.persistence.*
import wolfdesk.base.security.Password

@Entity
class Member(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val email: String,
    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    val password: Password,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    fun matches(password: Password) {
        check(this.password == password) { "회원 정보가 일치하지 않습니다.1" }
    }
}
