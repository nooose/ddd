package wolfdesk.tenant.integrate

import org.springframework.stereotype.Component
import wolfdesk.base.event.EventPublishAdaptor
import wolfdesk.base.event.system.InvitationSystemEvent
import wolfdesk.tenant.domain.invitation.TenantInviteConfirmedEvent

@Component
class InviteConfirmedEventPublishingAdapter : EventPublishAdaptor<TenantInviteConfirmedEvent, InvitationSystemEvent> {

    override fun convert(event: TenantInviteConfirmedEvent): InvitationSystemEvent {
        return InvitationSystemEvent(event.invitationId, 1, 1, InvitationSystemEvent.Type.ACCEPTED)
    }

    override fun supports(event: Any): Boolean {
        return event is TenantInviteConfirmedEvent
    }
}
