package wolfdesk.tenant.domain.invitation

abstract class InvitationDomainEvent

data class InvitedEvent(
    val id: Long,
    val tenantId: Long,
    val memberId: Long,
    val type: InvitationType
) : InvitationDomainEvent()

data class TenantInviteConfirmedEvent(
    val id: Long,
    val status: InvitationStatus,
) : InvitationDomainEvent()
