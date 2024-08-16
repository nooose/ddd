package wolfdesk.app.ticket.command.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.app.ticket.command.domain.Message
import wolfdesk.app.ticket.command.domain.Ticket
import wolfdesk.app.ticket.command.domain.TicketInformation
import wolfdesk.app.ticket.command.domain.TicketRepository

@Transactional
@Service
class TicketService(
    private val ticketRepository: TicketRepository,
) {

    fun create(command: TicketCreateCommand, principalId: Long) {
        val info = TicketInformation(
            title = command.title,
            description = command.description,
            supportCategoryId = command.supportCategoryId,
            createdById = principalId,
        )
        val ticket = Ticket(info)
        ticketRepository.save(ticket)
    }

    fun addMessage(ticketId: Long, command: MessageCreateCommand, principalId: Long) {
        val message = Message(command.body, principalId)
        val ticket = ticketRepository.findByIdOrNull(ticketId)
            ?: throw IllegalStateException("$ticketId 티켓을 찾을 수 없습니다.")
        ticket.add(message)
        ticketRepository.save(ticket)
    }
}
