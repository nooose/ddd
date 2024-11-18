package wolfdesk.member.integrate

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import wolfdesk.base.event.system.InvitationPubSystemEvent
import wolfdesk.member.domain.TenantAgentConfirmedEvent

@Component
class MemberEventSubscriptionAdapter(
    private val eventPublisher: ApplicationEventPublisher,
) {

    @EventListener
    fun convert(event: InvitationPubSystemEvent) {
        val systemEvent = when (event.type) {
            InvitationPubSystemEvent.Type.ACCEPTED -> TenantAgentConfirmedEvent(1, 1)
            else -> null
        }

        systemEvent?.let { eventPublisher.publishEvent(it) }
    }
}
