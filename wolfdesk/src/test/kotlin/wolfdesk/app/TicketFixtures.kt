package wolfdesk.app

import wolfdesk.ticket.command.application.MessageCreateCommand
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

fun createMessageAddCommandFixture(
    body: String = "테스트 메시지 내용"
): MessageCreateCommand {
    return MessageCreateCommand(
        body = body
    )
}
