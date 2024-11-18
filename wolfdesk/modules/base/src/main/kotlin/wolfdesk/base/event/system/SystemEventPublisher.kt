package wolfdesk.base.event.system

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import wolfdesk.base.event.EventPublishAdaptor

@Component
internal class SystemEventPublisher(
    private val delegator: ApplicationEventPublisher,
    private val adaptors: List<EventPublishAdaptor>
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun onApplicationEvent(event: Any) {
        adaptors.filter { adaptor -> adaptor.supports(event) }
            .map { adaptor -> adaptor.convert(event) }
            .forEach { systemEvent -> delegator.publishEvent(systemEvent) }
    }
}
