package wolfdesk.ticket.query

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.base.jpa.JdslRepository
import wolfdesk.ticket.command.domain.Ticket
import wolfdesk.ticket.command.domain.TicketInformation
import wolfdesk.ticket.command.domain.TicketRepository

@Transactional(readOnly = true)
@Service
class TicketQueryService(
    private val ticketRepository: TicketRepository,
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
        val ticket = ticketRepository.findByIdOrNull(id) ?:
            throw IllegalStateException("$id 티켓을 찾을 수 없습니다.")
        return TicketQuery.from(ticket)
    }
}
