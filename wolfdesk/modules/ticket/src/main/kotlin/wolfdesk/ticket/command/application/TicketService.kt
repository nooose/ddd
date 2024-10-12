package wolfdesk.ticket.command.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.ticket.command.domain.Message
import wolfdesk.ticket.command.domain.Ticket
import wolfdesk.ticket.command.domain.TicketInformation
import wolfdesk.ticket.command.domain.TicketRepository
import wolfdesk.ticket.integrate.TicketVerification

@Transactional
@Service
class TicketService(
    private val ticketVerification: TicketVerification,
    private val ticketRepository: TicketRepository,
) {

    fun create(tenantId: Long, command: TicketCreateCommand, principalId: Long) {
        ticketVerification.validate(tenantId, command.supportCategoryId, principalId)

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
        ticketRepository.save(ticket)
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
