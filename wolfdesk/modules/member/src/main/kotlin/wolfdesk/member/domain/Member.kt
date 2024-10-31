package wolfdesk.member.domain

import jakarta.persistence.*

@Entity
class Member(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val email: String,
    @Column(nullable = false)
    val password: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
