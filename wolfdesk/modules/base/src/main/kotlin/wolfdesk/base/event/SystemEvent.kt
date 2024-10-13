package wolfdesk.base.event

import java.util.*

abstract class SystemEvent {
    val eventId: UUID = UUID.randomUUID()
    val timestamp: Long = System.currentTimeMillis()
}
