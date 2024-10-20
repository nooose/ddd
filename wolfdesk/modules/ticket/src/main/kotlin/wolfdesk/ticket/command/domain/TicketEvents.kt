package wolfdesk.ticket.command.domain

import java.util.*

data class TicketCreatedEvent(
    val ticket: Ticket,
    val timestamp: Long = System.currentTimeMillis()
)

data class TicketOpenedEvent(
    val ticketId: Long,
    val timestamp: Long = System.currentTimeMillis()
)

data class MessageAddedEvent(
    val message: Message,
    val timestamp: Long = System.currentTimeMillis()
)

data class MessageDeletedEvent(
    val messageId: UUID,
    val timestamp: Long = System.currentTimeMillis()
)

data class TicketAssignedEvent(
    val ticketId: Long,
    val agentId: Long,
    val timestamp: Long = System.currentTimeMillis()
)
