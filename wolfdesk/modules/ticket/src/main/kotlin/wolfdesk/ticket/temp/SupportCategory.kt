package wolfdesk.ticket.temp

import jakarta.persistence.*

@Table(name = "support_category")
@Entity
class SupportCategory(
    @Column(nullable = false)
    val name: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
