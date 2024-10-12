package wolfdesk.tenant.application

import wolfdesk.tenant.domain.invitation.InvitationStatus
import wolfdesk.tenant.domain.invitation.InvitationType

data class InvitationCommand(
    val memberId: Long,
    val type: InvitationType,
)

data class TenantDecisionCommand(
    val invitationId: Long,
    val status: InvitationStatus,
)
