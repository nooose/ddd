package wolfdesk.ticket.query

import wolfdesk.ticket.command.domain.Ticket
import java.time.LocalDateTime
import java.util.*

data class TicketSimpleQuery(
    val id: Long,
    val title: String,
    val createdAt: LocalDateTime,
)

data class TicketQuery(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    var messages: List<Message> = emptyList()
) {
    data class Message(
        val id: UUID,
        val body: String,
        val createdBy: Long,
    )

    companion object {
        fun from(entity: Ticket): TicketQuery {
            return TicketQuery(
                id = entity.id,
                title = entity.information.title,
                description = entity.information.description,
                createdAt = entity.information.createdAt,
                messages = entity.messages.map {
                    Message(
                        id = it.id,
                        body = it.body,
                        createdBy = it.createdById,
                    )
                }
            )
        }
    }
}
