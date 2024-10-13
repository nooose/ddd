package wolfdesk.tenant.domain.invitation

enum class InvitationStatus {
    SENT,
    ACCEPTED,
    REJECTED,
    ;

    val isAccepted: Boolean
        get() = this == ACCEPTED
}
