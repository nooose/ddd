package wolfdesk.app

import wolfdesk.ticket.command.application.TicketCreateCommand

fun createTicketCreateCommandFixture(
    title: String = "티켓 이름",
    description: String = "티켓 설명",
    tenantId: Long = 1L,
    supportCategoryId: Long = 1L,
): TicketCreateCommand {
    return TicketCreateCommand(
        title = title,
        description = description,
        tenantId = tenantId,
        supportCategoryId = supportCategoryId,
    )
}
