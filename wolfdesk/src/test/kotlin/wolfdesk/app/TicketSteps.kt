package wolfdesk.app

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import wolfdesk.ticket.command.application.MessageCreateCommand
import wolfdesk.ticket.command.application.TicketCreateCommand

fun 티켓생성(command: TicketCreateCommand): Response {
    return Given {
        body(command)
    } When {
        post("/tickets")
    }
}

fun 티켓목록조회(): Response {
    return Given {
        and()
    } When {
        get("/tickets")
    }
}

fun 티켓상세조회(ticketId: Long): Response {
    return Given {
        and()
    } When {
        get("/tickets/$ticketId")
    }
}

fun 티켓상세조회(ticketLocation: String): Response {
    return Given {
        and()
    } When {
        get(ticketLocation)
    }
}

fun 메시지등록(ticketId: Long, command: MessageCreateCommand): Response {
    return Given {
        body(command)
    } When {
        post("/tickets/${ticketId}/messages")
    }
}

fun 메시지등록(ticketLocation: String, command: MessageCreateCommand): Response {
    return Given {
        body(command)
    } When {
        post("$ticketLocation/messages")
    }
}

fun 메시지삭제(ticketLocation: String, messageId: String): Response {
    return Given {
        and()
    } When {
        delete("$ticketLocation/messages/$messageId")
    }
}
