package wolfdesk.app.ticket.query

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.app.ticket.command.domain.Ticket
import wolfdesk.app.ticket.command.domain.TicketInformation
import wolfdesk.app.config.JdslRepository

@Transactional(readOnly = true)
@Service
class TicketQueryService(
        private val jdslRepository: JdslRepository,
) {

    fun getAll(): List<TicketQuery> {
        return jdslRepository.findAll {
            selectNew<TicketQuery>(
                path(Ticket::id),
                path(Ticket::information)(TicketInformation::title),
                path(Ticket::information)(TicketInformation::createdAt),
            ).from(entity(Ticket::class))
        }
    }
}
