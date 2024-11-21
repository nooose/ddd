package wolfdesk.base.event

import wolfdesk.base.event.system.SystemEvent

interface EventPublishAdaptor<T : Any, R : SystemEvent> {

    fun convert(event: T): R

    fun supports(event: Any): Boolean
}
