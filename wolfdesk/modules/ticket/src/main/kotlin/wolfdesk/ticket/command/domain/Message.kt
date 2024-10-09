package wolfdesk.ticket.command.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Table(name = "ticket_message")
@Entity
class Message(
    @Column(nullable = false, columnDefinition = "TEXT")
    var body: String,
    @Column(nullable = false)
    val createdById: Long,
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Id @Column(columnDefinition = "binary(16)")
    val id: UUID = UUID.randomUUID()
)
