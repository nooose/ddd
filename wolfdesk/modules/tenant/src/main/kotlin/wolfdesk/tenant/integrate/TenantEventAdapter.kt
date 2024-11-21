package wolfdesk.tenant.integrate

import org.springframework.stereotype.Component
import wolfdesk.base.event.EventPublishAdaptor
import wolfdesk.base.event.system.TenantSystemEvent
import wolfdesk.tenant.domain.tenant.TenantCreatedEvent

@Component
class TenantEventAdapter : EventPublishAdaptor<TenantCreatedEvent, TenantSystemEvent> {
    override fun convert(event: TenantCreatedEvent): TenantSystemEvent {
        return TenantSystemEvent(event.id, TenantSystemEvent.Type.CREATED)
    }

    override fun supports(event: Any): Boolean {
        return event is TenantCreatedEvent
    }
}
