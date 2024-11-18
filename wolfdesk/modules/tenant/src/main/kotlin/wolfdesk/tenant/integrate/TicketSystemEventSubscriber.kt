package wolfdesk.tenant.integrate

import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component
import wolfdesk.base.event.TicketSystemEvent

@Component
class TicketSystemEventSubscriber {

    @ApplicationModuleListener
    fun handle(event: TicketSystemEvent) {
        println("${event.type} ${event.ticketId}")
    }
}
