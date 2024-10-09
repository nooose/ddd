package wolfdesk.ticket.temp

import jakarta.persistence.*

@Table(name = "agent")
@Entity
class Agent(
    @Column(nullable = false)
    val name: String,
    val tenantId: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
