package wolfdesk.app

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.springframework.http.HttpHeaders
import wolfdesk.tenant.application.CategoryCreateCommand
import wolfdesk.tenant.application.TenantCreateCommand

fun 테넌트생성(jwt: String, command: TenantCreateCommand): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        body(command)
    } When {
        post("/tenants")
    }
}

fun 카테고리추가(jwt: String, tenantId: Long, command: CategoryCreateCommand): Response {
    return Given {
        header(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        body(command)
    } When {
        post("/tenants/$tenantId/categories")
    }
}
