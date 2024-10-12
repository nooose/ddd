package wolfdesk.ticket.integrate

interface TicketVerification {
    fun validate(tenantId: Long, memberId: Long, categoryId: Long)
}
