package wolfdesk.app.ticket.command.domain

import jakarta.persistence.*

@Table(name = "ticket_message")
@Entity
class Message(
    @Column(nullable = false, columnDefinition = "TEXT")
    val body: String,
    @Column(nullable = false)
    val createdById: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
)
