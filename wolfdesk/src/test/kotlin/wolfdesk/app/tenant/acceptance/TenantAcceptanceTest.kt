package wolfdesk.app.tenant.acceptance

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import wolfdesk.app.*
import wolfdesk.base.event.system.TenantPubSystemEvent
import wolfdesk.tenant.domain.tenant.TenantCreatedEvent

@DisplayName("테넌트 인수 테스트")
@AcceptanceTest
class TenantAcceptanceTest(
    @LocalServerPort
    private val port: Int,
    private val eventRecords: EventRecords,
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    var jwt = ""

    beforeSpec {
        initRestAssured(port)
        jwt = 로그인("test@test.com", "1234")
    }


    Given("테넌트를 생성하고") {
        val response = 테넌트생성(jwt, createTenantCommandFixture("삼성")) status HttpStatus.CREATED
        eventRecords.count<TenantCreatedEvent>() shouldBe 1
        eventRecords.count<TenantPubSystemEvent>() shouldBe 1

        Then("카테고리를 추가할 수 있다.") {
            카테고리추가(jwt, 1, createCategoryCommandFixture("IT")) status HttpStatus.OK
        }
    }

    afterContainer {
        DatabaseCleanerUtil.truncate()
    }
})
