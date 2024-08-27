package wolfdesk.app.ticket.command.infra

import org.springframework.stereotype.Component
import wolfdesk.app.ticket.command.domain.AssignPolicy
import java.util.Random

@Component
class RandomAssignPolicy : AssignPolicy {
    override fun pickAssignee(ticketId: Long): Long {
        return RANDOM_INSTANCE.nextInt(100).toLong()
    }

    companion object {
        private val RANDOM_INSTANCE = Random()
    }
}
