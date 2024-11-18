package wolfdesk.tenant.integrate

import org.springframework.stereotype.Component
import wolfdesk.base.event.EventPublishAdaptor
import wolfdesk.base.event.system.InvitationPubSystemEvent
import wolfdesk.base.event.system.PubSystemEvent
import wolfdesk.tenant.domain.invitation.TenantInviteConfirmedEvent

@Component
class InviteConfirmedEventPublishingAdapter : EventPublishAdaptor {

    override fun convert(event: Any): PubSystemEvent {
        event as TenantInviteConfirmedEvent
        return InvitationPubSystemEvent(event.invitationId, 1, 1, InvitationPubSystemEvent.Type.ACCEPTED)
    }

    override fun supports(event: Any): Boolean {
        return event is TenantInviteConfirmedEvent
    }
}
