package wolfdesk.tenant.domain.invitation

data class InvitedEvent(
    val id: Long,
    val tenantId: Long,
    val memberId: Long,
    val type: InvitationType
)

