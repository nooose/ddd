package wolfdesk.base.event

data class InvitationSystemEvent(
    val invitationId: Long,
    val tenantId: Long,
    val inviteeId: Long,
    val type: Type,
) : SystemEvent() {

    enum class Type {
        ACCEPTED,
        REJECTED,
    }
}
