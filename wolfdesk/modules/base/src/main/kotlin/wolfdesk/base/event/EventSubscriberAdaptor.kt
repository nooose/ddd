package wolfdesk.base.event

import wolfdesk.base.event.system.SubSystemEvent

interface EventSubscriberAdaptor {
    fun convert(event: Any): SubSystemEvent

    fun supports(event: Any): Boolean
}
