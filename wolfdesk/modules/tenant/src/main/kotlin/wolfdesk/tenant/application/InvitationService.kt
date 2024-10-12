package wolfdesk.tenant.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.tenant.domain.invitation.Invitation
import wolfdesk.tenant.domain.invitation.InvitationRepository
import wolfdesk.tenant.domain.tenant.Tenant
import wolfdesk.tenant.domain.tenant.TenantRepository

@Transactional
@Service
class InvitationService(
    private val tenantRepository: TenantRepository,
    private val invitationRepository: InvitationRepository,
) {

    fun inviteMember(tenantId: Long, command: InvitationCommand, principalId: Long) {
        val tenant = getTenant(tenantId)
        tenant.validateOwner(principalId)

        val invitation = Invitation(tenantId, command.memberId, command.type)
        invitationRepository.save(invitation)
    }

    fun changeStatus(command: TenantDecisionCommand, principalId: Long) {
        val invitation = getInvitation(command.invitationId)
        invitation.changeStatus(command.status, principalId)
    }

    private fun getTenant(tenantId: Long): Tenant {
        return tenantRepository.findByIdOrNull(tenantId)
            ?: throw IllegalStateException("$tenantId 테넌트를 찾을 수 없습니다.")
    }

    private fun getInvitation(invitationId: Long): Invitation {
        return invitationRepository.findByIdOrNull(invitationId)
            ?: throw IllegalStateException("$invitationId 초대를 찾을 수 없습니다.")
    }
}
