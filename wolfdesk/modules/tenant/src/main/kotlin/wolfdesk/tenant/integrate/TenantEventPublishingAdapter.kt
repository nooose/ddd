package wolfdesk.tenant.integrate

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.modulith.NamedInterface
import org.springframework.stereotype.Component
import wolfdesk.base.event.InvitationSystemEvent
import wolfdesk.tenant.domain.invitation.InvitationDomainEvent
import wolfdesk.tenant.domain.invitation.InvitedEvent
import wolfdesk.tenant.domain.invitation.TenantInviteConfirmedEvent

@NamedInterface("tenant-integrate")
@Component
class TenantEventPublishingAdapter(
    private val eventPublisher: ApplicationEventPublisher,
) {

    @EventListener
    fun convert(event: InvitationDomainEvent) {
        val systemEvent = when (event) {
            is InvitedEvent -> null
            is TenantInviteConfirmedEvent -> InvitationSystemEvent(event.id, 1, 1, InvitationSystemEvent.Type.ACCEPTED)
            else -> null
        }

        systemEvent?.let { eventPublisher.publishEvent(it) }
    }
}
