package wolfdesk.ticket.command.application

import org.springframework.context.event.EventListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import wolfdesk.ticket.command.domain.AssignPolicy
import wolfdesk.ticket.command.domain.Ticket
import wolfdesk.ticket.command.domain.TicketOpenedEvent
import wolfdesk.ticket.command.domain.TicketRepository

@Component
class AutoPilotService(
    private val assignPolicy: AssignPolicy,
    private val ticketRepository: TicketRepository,
) {

    @EventListener
    fun handle(event: TicketOpenedEvent) {
        val ticketId = event.ticketId
        val agentId = assignPolicy.pickAssigner(ticketId)
        val ticket = getTicket(ticketId)
        ticket.assign(agentId)
    }

    private fun getTicket(ticketId: Long): Ticket {
        return ticketRepository.findByIdOrNull(ticketId)
            ?: throw IllegalStateException("$ticketId 티켓을 찾을 수 없습니다.")
    }
}
