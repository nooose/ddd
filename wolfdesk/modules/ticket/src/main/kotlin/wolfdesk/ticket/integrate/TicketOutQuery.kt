package wolfdesk.ticket.integrate

import java.time.LocalDateTime

data class TicketOutQuery(
    val id: Long,
    val title: String,
    val description: String,
    val supportCategoryId: Int,
    val createdBy: Long,
    val priority: String,
    val state: String,
    val closedBy: Long,
    val agentId: Long,
    val createdAt: LocalDateTime,
)
