package wolfdesk.app.ticket

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.filter.log.LogDetail
import org.springframework.http.MediaType

fun initRestAssured(port: Int) {
    RestAssured.requestSpecification = RequestSpecBuilder()
        .setPort(port)
        .setContentType(MediaType.APPLICATION_JSON_VALUE)
        .log(LogDetail.ALL)
        .build()
    RestAssured.responseSpecification = ResponseSpecBuilder()
        .log(LogDetail.ALL)
        .build()
}

fun objectMapper() : ObjectMapper {
    val objectMapper = ObjectMapper()
    return objectMapper
}
