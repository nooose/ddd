package wolfdesk.base.event

data class InvitationSystemEvent(
    val invitationId: Long,
    val type: Type,
) : SystemEvent() {

    enum class Type {
        ACCEPTED,
    }
}
