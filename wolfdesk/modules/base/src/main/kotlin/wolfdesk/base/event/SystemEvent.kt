package wolfdesk.base.event

import java.util.*

abstract class SystemEvent {
    val timestamp: Long = System.currentTimeMillis()
    val uuid: UUID = UUID.randomUUID()
}
