package wolfdesk.app.ticket.query

import java.time.LocalDateTime

data class TicketQuery(
    val id: Long,
    val title: String,
    val createdAt: LocalDateTime,
)
