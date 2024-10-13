package wolfdesk.base.event

data class TicketSystemEvent(
    val ticketId: Long,
    val type: Type,
) {
    enum class Type {
        CREATED,
        DELETED,
        COMPLETED,
        TERMINATED,
        AGENT_ASSIGNED,
        ESCALATED,
    }
}
