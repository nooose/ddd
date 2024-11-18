package wolfdesk.tenant.domain.invitation

abstract class InvitationDomainEvent

data class InvitedEvent(
    val id: Long,
    val tenantId: Long,
    val memberId: Long,
    val type: InvitationType
)

data class TenantInviteConfirmedEvent(
    val invitationId: Long,
    val status: InvitationStatus,
)
