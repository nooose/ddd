package wolfdesk.ticket.integrate

import org.springframework.stereotype.Component
import wolfdesk.tenant.integrate.TenantData
import wolfdesk.tenant.integrate.TenantIntegrationService

@Component
class TicketValidator(
    private val tenantIntegrationService: TenantIntegrationService,
): TicketVerification {

    override fun validate(tenantId: Long, memberId: Long, categoryId: Long) {
        val tenant = tenantIntegrationService.getTenantsByMemberId(memberId)
            .first { it.id == tenantId }

        if (!tenant.hasCategory(categoryId)) {
            throw IllegalStateException("${tenant.name} 에서 지원하지 않는 카테고리입니다.")
        }
    }

    private fun TenantData.hasCategory(categoryId: Long): Boolean {
        return this.supportCategories.map { it.id }
            .contains(categoryId)
    }
}
