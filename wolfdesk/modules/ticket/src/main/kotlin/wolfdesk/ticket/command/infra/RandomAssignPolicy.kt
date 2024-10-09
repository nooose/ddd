package wolfdesk.ticket.command.infra

import org.springframework.stereotype.Component
import wolfdesk.ticket.command.domain.AssignPolicy
import java.util.*

@Component
class RandomAssignPolicy : AssignPolicy {
    override fun pickAssigner(ticketId: Long): Long {
        return RANDOM_INSTANCE.nextInt(100).toLong()
    }

    companion object {
        private val RANDOM_INSTANCE = Random()
    }
}
