package wolfdesk.app.ticket.query

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.app.ticket.command.domain.Ticket
import wolfdesk.app.ticket.command.domain.TicketInformation
import wolfdesk.app.config.JdslRepository
import java.lang.IllegalStateException

@Transactional(readOnly = true)
@Service
class TicketQueryService(
        private val jdslRepository: JdslRepository,
) {

    fun getAll(): List<TicketSimpleQuery> {
        return jdslRepository.findAll {
            selectNew<TicketSimpleQuery>(
                path(Ticket::id),
                path(Ticket::information)(TicketInformation::title),
                path(Ticket::information)(TicketInformation::createdAt),
            ).from(entity(Ticket::class))
        }
    }

    fun getOne(id: Long): TicketQuery {
        return jdslRepository.findOne {
            selectNew<TicketQuery>(
                path(Ticket::id),
                path(Ticket::information)(TicketInformation::title),
                path(Ticket::information)(TicketInformation::description),
                path(Ticket::information)(TicketInformation::createdAt),
            ).from(entity(Ticket::class)
            ).where(path(Ticket::id).equal(longLiteral(id)))
        } ?: throw IllegalStateException("$id 티켓을 찾을 수 없습니다.")
    }
}
