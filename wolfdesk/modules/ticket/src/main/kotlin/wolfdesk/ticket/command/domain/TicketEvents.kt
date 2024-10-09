package wolfdesk.ticket.command.domain

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

data class TicketAssignedEvent(
    val ticketId: Long,
    val agentId: Long,
    val timestamp: Long = System.currentTimeMillis()
)
