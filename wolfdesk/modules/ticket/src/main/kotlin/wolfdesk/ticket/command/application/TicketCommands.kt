package wolfdesk.ticket.command.application

import jakarta.validation.constraints.NotBlank

data class TicketCreateCommand(
    @field:NotBlank
    val title: String,
    val description: String,
    val tenantId: Long,
    val supportCategoryId: Long,
)

data class MessageCreateCommand(
    @field:NotBlank
    val body: String,
)
