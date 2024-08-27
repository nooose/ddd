package wolfdesk.app.ticket.command.application

import org.springframework.context.event.EventListener
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import wolfdesk.app.ticket.command.domain.*

@Component
class AutoPilotService(
    private val assignPolicy: AssignPolicy,
    private val ticketRepository: TicketRepository,
) {

    @EventListener
    fun handle(event: TicketCreatedEvent) {
        val ticketId = event.ticket.id
        val agentId = assignPolicy.pickAssignee(ticketId)
        val ticket = getTicket(ticketId)
        ticket.assign(agentId)
    }

    private fun getTicket(ticketId: Long): Ticket {
        return ticketRepository.findByIdOrNull(ticketId)
            ?: throw IllegalStateException("$ticketId 티켓을 찾을 수 없습니다.")
    }
}
