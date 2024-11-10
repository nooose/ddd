package wolfdesk.app.ticket.acceptance

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import wolfdesk.app.*
import wolfdesk.ticket.command.domain.TicketOpenedEvent

@DisplayName("티켓 인수 테스트")
@AcceptanceTest
class TicketAcceptanceTest(
    @LocalServerPort
    private val port: Int,
    private val eventRecords: EventRecords,
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    beforeSpec {
        initRestAssured(port)
    }

    Given("티켓을 여러개 생성하면") {
        val command1 = createTicketCreateCommandFixture(title = "테스트 티켓1")
        val command2 = createTicketCreateCommandFixture(title = "테스트 티켓2")
        티켓생성(command1) status HttpStatus.CREATED
        티켓생성(command2) status HttpStatus.CREATED

        Then("티켓 목록을 조회할 수 있다.") {
            val response = 티켓목록조회() status HttpStatus.OK
            val titles = response.getList<String>("data.title")
            titles shouldBe listOf("테스트 티켓1", "테스트 티켓2")
        }
    }

    Given("티켓을 생성하고") {
        val command = createTicketCreateCommandFixture()
        val location = 티켓생성(command).header(HttpHeaders.LOCATION)
        var messageId = ""

        When("티켓에 메시지를 입력하면") {
            val messageCommand = createMessageAddCommandFixture()
            메시지등록(location, messageCommand) status HttpStatus.OK

            Then("메시지를 확인할 수 있다.") {
                val response = 티켓상세조회(location) status HttpStatus.OK
                response.getList<Any>("data.messages") shouldHaveSize 1
                messageId = response.getString("data.messages[0].id")
            }
        }

        When("메시지를 삭제하면") {
            메시지삭제(location, messageId)

            Then("메시지를 확인할 수 없다.") {
                val response = 티켓상세조회(location) status HttpStatus.OK
                response.getList<Any>("data.messages") shouldHaveSize 0
            }
        }

        When("티켓을 열면") {
            티켓열림(location)

            Then("티켓 열림 이벤트가 발행된다.") {
                eventRecords.count<TicketOpenedEvent>() shouldBe 1
            }
        }
    }

    afterContainer {
        DatabaseCleanerUtil.truncate()
        eventRecords.clear()
    }
})
