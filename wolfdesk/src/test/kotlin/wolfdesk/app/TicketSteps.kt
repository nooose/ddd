package wolfdesk.app

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.springframework.http.HttpHeaders
import wolfdesk.ticket.command.application.MessageCreateCommand
import wolfdesk.ticket.command.application.TicketCreateCommand

fun 티켓생성(jwt: String, command: TicketCreateCommand): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        body(command)
    } When {
        post("/tickets")
    }
}

fun 티켓열림(jwt: String, ticketId: Long): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
    } When {
        post("/tickets/$ticketId/open")
    }
}

fun 티켓열림(jwt: String, ticketLocation: String): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
    } When {
        post("$ticketLocation/open")
    }
}

fun 티켓목록조회(jwt: String, ): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
    } When {
        get("/tickets")
    }
}

fun 티켓상세조회(jwt: String, ticketId: Long): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
    } When {
        get("/tickets/$ticketId")
    }
}

fun 티켓상세조회(jwt: String, ticketLocation: String): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
    } When {
        get(ticketLocation)
    }
}

fun 메시지등록(jwt: String ,ticketId: Long, command: MessageCreateCommand): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        body(command)
    } When {
        post("/tickets/${ticketId}/messages")
    }
}

fun 메시지등록(jwt: String, ticketLocation: String, command: MessageCreateCommand): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        body(command)
    } When {
        post("$ticketLocation/messages")
    }
}

fun 메시지삭제(jwt: String, ticketLocation: String, messageId: String): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        and()
    } When {
        delete("$ticketLocation/messages/$messageId")
    }
}
