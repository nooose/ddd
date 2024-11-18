package wolfdesk.tenant.integrate

import org.springframework.stereotype.Component
import wolfdesk.base.event.EventPublishAdaptor
import wolfdesk.base.event.system.PubSystemEvent
import wolfdesk.base.event.system.TenantPubSystemEvent
import wolfdesk.tenant.domain.tenant.TenantCreatedEvent

@Component
class TenantEventAdapter : EventPublishAdaptor {
    override fun convert(event: Any): PubSystemEvent {
        event as TenantCreatedEvent
        return TenantPubSystemEvent(event.id, TenantPubSystemEvent.Type.CREATED)
    }

    override fun supports(event: Any): Boolean {
        return event is TenantCreatedEvent
    }
}
