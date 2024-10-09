package wolfdesk.ticket.command.application

data class TicketCreateCommand(
    val title: String,
    val description: String,
    val supportCategoryId: Long,
)

data class MessageCreateCommand(
    val body: String,
)
