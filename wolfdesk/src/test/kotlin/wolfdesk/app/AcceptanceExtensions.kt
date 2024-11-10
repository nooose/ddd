package wolfdesk.app

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

fun initRestAssured(port: Int) {
    RestAssured.requestSpecification = RequestSpecBuilder()
        .setPort(port)
        .setUrlEncodingEnabled(true)
        .setContentType(MediaType.APPLICATION_JSON_VALUE)
        .log(LogDetail.ALL)
        .build()
    RestAssured.responseSpecification = ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .build()
}

infix fun Response.status(status: HttpStatus): JsonPath {
    return this Then {
        statusCode(status.value())
    } Extract {
        jsonPath()
    }
}

fun Response.header(header: HttpHeaders): String {
    return Extract { header(header) }
}
