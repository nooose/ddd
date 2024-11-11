package wolfdesk.app

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.springframework.http.HttpStatus
import wolfdesk.member.application.command.MemberJoinCommand
import wolfdesk.member.application.command.MemberLoginCommand

fun 로그인(email: String, password: String): String {
    가입(createJoinCommandFixture(name = "김영철", email = email, password = password))
    val response = 로그인(createLoginCommandFixture(email, password)) status HttpStatus.OK
    return response.getString("data.accessToken")
}

fun 가입(command: MemberJoinCommand = createJoinCommandFixture()): Response {
    return Given {
        body(command)
    } When {
        post("/members")
    }
}

fun 로그인(command: MemberLoginCommand = createLoginCommandFixture()): Response {
    return Given {
        body(command)
    } When {
        post("/auth/token")
    }
}
