package wolfdesk.tenant.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import wolfdesk.tenant.domain.tenant.SupportCategory
import wolfdesk.tenant.domain.tenant.SupportCategoryRepository
import wolfdesk.tenant.domain.tenant.Tenant
import wolfdesk.tenant.domain.tenant.TenantRepository

@Service
class SupportCategoryService(
    private val tenantRepository: TenantRepository,
    private val supportCategoryRepository: SupportCategoryRepository,
) {

    fun addSupportCategory(tenantId: Long, command: CategoryCreateCommand, principalId: Long) {
        validateAddCategory(tenantId, command.name, principalId)
        val category = SupportCategory(
            name = command.name,
            tenantId = tenantId,
        )
        supportCategoryRepository.save(category)
    }

    private fun validateAddCategory(tenantId: Long, name: String, principalId: Long) {
        val tenant = getTenant(tenantId)
        tenant.validateOwner(principalId)
        val category = supportCategoryRepository.findByNameAndTenantId(tenantId = tenantId, name = name)
        check(category == null) { "중복된 카테고리가 존재합니다." }
    }

    private fun getTenant(tenantId: Long): Tenant {
        return tenantRepository.findByIdOrNull(tenantId)
            ?: throw IllegalStateException("$tenantId 테넌트를 찾을 수 없습니다.")
    }
}
