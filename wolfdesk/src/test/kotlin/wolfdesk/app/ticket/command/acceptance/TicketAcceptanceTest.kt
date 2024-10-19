package wolfdesk.app.ticket.command.acceptance

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import wolfdesk.app.*
import wolfdesk.app.ticket.initRestAssured

@DisplayName("티켓 인수 테스트")
@AcceptanceTest
class TicketAcceptanceTest(
    @LocalServerPort
    val port: Int = 0
) : BehaviorSpec({

    beforeContainer {
        initRestAssured(port)
    }

    Given("티켓을 여러개 생성하면") {
        val command1 = createTicketCreateCommandFixture(title = "테스트 티켓1")
        val command2 = createTicketCreateCommandFixture(title = "테스트 티켓2")
        티켓생성요청(command1) status HttpStatus.OK
        티켓생성요청(command2) status HttpStatus.OK

        Then("티켓 목록을 조회할 수 있다.") {
            val response = 티켓목록조회요청() status HttpStatus.OK
            val titles = response.getList<String>("title")
            titles shouldBe listOf("테스트 티켓1", "테스트 티켓2")
        }
    }

    afterContainer {
        DatabaseCleanerUtil.truncate()
    }
})
