package wolfdesk.app.ticket.command.domain

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestConstructor
import wolfdesk.ticket.command.domain.*

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TicketRepositoryTest(
    private val repository: TicketRepository,
) : StringSpec({

    "티켓을 저장하면 메시지도 같이 저장된다." {
        val info = TicketInformation(
            title = "티켓 제목",
            description = "티켓 내용",
            createdById = 1L,
            state = State.OPEN,
            supportCategoryId = 1L
        )
        val message = Message(
            body = "메시지 내용",
            createdById = 1L,
        )
        val ticket = Ticket(information = info, listOf(message))

        repository.save(ticket)

        val findTicket = repository.findByIdOrNull(ticket.id)!!
        assertSoftly {
            findTicket.messages.size shouldBe 1
            findTicket.messages[0].body shouldBe "메시지 내용"
        }
    }
})
