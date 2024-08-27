package wolfdesk.app.ticket.command.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.app.ticket.command.domain.*

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

    fun open(ticketId: Long, principalId: Long) {
        val ticket = getTicket(ticketId)
        ticket.open(principalId)
    }

    fun addMessage(ticketId: Long, command: MessageCreateCommand, principalId: Long) {
        val message = Message(command.body, principalId)
        val ticket = getTicket(ticketId)
        ticket.add(message)
        ticketRepository.save(ticket)
    }

    private fun getTicket(ticketId: Long): Ticket {
        return ticketRepository.findByIdOrNull(ticketId)
            ?: throw IllegalStateException("$ticketId 티켓을 찾을 수 없습니다.")
    }
}
