package wolfdesk.base.event

import wolfdesk.base.event.system.PubSystemEvent

interface EventPublishAdaptor {
    fun convert(event: Any): PubSystemEvent

    fun supports(event: Any): Boolean
}
