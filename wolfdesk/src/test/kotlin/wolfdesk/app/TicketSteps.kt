package wolfdesk.app

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.springframework.http.HttpStatus
import wolfdesk.ticket.command.application.TicketCreateCommand

fun 티켓생성요청(command: TicketCreateCommand): Response {
    return Given {
        body(command)
    } When {
        post("/tickets")
    }
}

fun 티켓목록조회요청(): Response {
    return Given {
        and()
    } When {
        get("/tickets")
    }
}

infix fun Response.status(status: HttpStatus): JsonPath {
    return this Then {
        statusCode(status.value())
    } Extract {
        jsonPath()
    }
}
