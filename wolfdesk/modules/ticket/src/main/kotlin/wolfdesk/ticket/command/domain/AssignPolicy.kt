package wolfdesk.ticket.command.domain

interface AssignPolicy {

    fun pickAssigner(ticketId: Long): Long
}
