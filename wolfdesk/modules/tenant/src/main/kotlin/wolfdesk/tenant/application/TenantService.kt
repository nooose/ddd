package wolfdesk.tenant.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.tenant.domain.tenant.Tenant
import wolfdesk.tenant.domain.tenant.TenantRepository

@Transactional
@Service
class TenantService(
    private val tenantRepository: TenantRepository,
) {

    fun createTenant(command: TenantCreateCommand, principalId: Long) {
        val findTenant = tenantRepository.findByName(command.name)
        check(findTenant == null) { "${command.name} 이름은 중복입니다." }

        val newTenant = Tenant(
            name = command.name,
            createdBy = principalId
        )
        tenantRepository.save(newTenant)
    }
}
