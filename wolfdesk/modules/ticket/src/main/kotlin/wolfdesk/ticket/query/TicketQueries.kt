package wolfdesk.ticket.query

import java.time.LocalDateTime

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
)
