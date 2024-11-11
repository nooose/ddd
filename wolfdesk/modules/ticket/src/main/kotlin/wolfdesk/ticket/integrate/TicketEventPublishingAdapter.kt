package wolfdesk.ticket.integrate

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import wolfdesk.base.event.TicketSystemEvent
import wolfdesk.ticket.command.domain.TicketCreatedEvent

@Component
class TicketEventPublishingAdapter(
    private val eventPublisher: ApplicationEventPublisher,
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun convert(event: TicketCreatedEvent) {
        eventPublisher.publishEvent(
            TicketSystemEvent(
                ticketId = event.ticket.id,
                type = TicketSystemEvent.Type.CREATED
            )
        )
    }
}
