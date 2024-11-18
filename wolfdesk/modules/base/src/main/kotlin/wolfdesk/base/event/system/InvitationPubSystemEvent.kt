package wolfdesk.base.event.system

data class InvitationPubSystemEvent(
    val invitationId: Long,
    val tenantId: Long,
    val inviteeId: Long,
    val type: Type,
) : PubSystemEvent() {

    enum class Type {
        ACCEPTED,
        REJECTED,
    }
}
