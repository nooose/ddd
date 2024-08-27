package wolfdesk.app.ticket.command.domain

interface AssignPolicy {

    fun pickAssignee(ticketId: Long): Long
}
