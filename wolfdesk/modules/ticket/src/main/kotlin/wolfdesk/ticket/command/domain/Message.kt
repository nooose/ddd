package wolfdesk.ticket.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
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
    @Id @Column
    val id: UUID = UUID.randomUUID()
) {

    fun validateOwner(ownerId: Long) {
        check(createdById == ownerId) { "메시지 작성자가 아닙니다." }
    }
}
