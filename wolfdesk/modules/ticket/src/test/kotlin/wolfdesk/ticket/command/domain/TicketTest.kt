package wolfdesk.ticket.command.domain

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class TicketTest : StringSpec({

    "티켓을 생성할 수 있다." {
        shouldNotThrowAny {
            createTicketFixture()
        }
    }

    "티켓 생성자만 티켓을 열 수 있다." {
        val 생성자ID = 1L
        val ticket = createTicketFixture(createdBy = 생성자ID)

        assertSoftly {
            shouldThrow<IllegalStateException> { ticket.open(2) }
            shouldNotThrow<Exception> { ticket.open(생성자ID) }
        }
    }

    "티켓에 메시지를 작성하고 지울 수 있다." {
        val ticket = createTicketFixture()
        val message1 = createMessageFixture(createdBy = 1)
        val message2 = createMessageFixture(createdBy = 1)

        ticket.addMessage(message1)
        ticket.addMessage(message2)
        ticket.deleteMessage(message2.id, 1)

        val message = ticket.messages.first()

        assertSoftly {
            ticket.messages shouldHaveSize 1
            message.id shouldBe message1.id
        }
    }

    "티켓 메시지 삭제 시 메시지 생성자가 아니면 예외를 던진다." {
        val ticket = createTicketFixture()
        val message = createMessageFixture(createdBy = 1)
        ticket.addMessage(message)

        shouldThrow<IllegalStateException> {
            ticket.deleteMessage(message.id, 2)
        }
    }

    "티켓을 열 수 있다." {
        val ticket = createTicketFixture()

        shouldNotThrowAny { ticket.open(1) }
        ticket.information.isOpened.shouldBeTrue()
    }
})

fun createTicketFixture(createdBy: Long = 1): Ticket {
    val information = TicketInformation(
        title = "티켓 제목",
        description = "티켓 설명",
        supportCategoryId = 1,
        createdById = createdBy,
    )
    return Ticket(
        information = information,
    )
}

fun createMessageFixture(createdBy: Long = 1): Message {
    return Message(
        body = "테스트 메시지 내용",
        createdById = createdBy,
    )
}
