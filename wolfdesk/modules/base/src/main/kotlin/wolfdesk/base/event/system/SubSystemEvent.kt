package wolfdesk.base.event.system

import java.util.*

abstract class SubSystemEvent {
    val eventId: UUID = UUID.randomUUID()
    val timestamp: Long = System.currentTimeMillis()
}
