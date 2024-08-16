package wolfdesk.app.ticket.command.domain

data class TicketCreatedEvent(
    val ticket: Ticket,
    val timestamp: Long = System.currentTimeMillis()
)

data class MessageCreatedEvent(
    val message: Message,
    val timestamp: Long = System.currentTimeMillis()
)
