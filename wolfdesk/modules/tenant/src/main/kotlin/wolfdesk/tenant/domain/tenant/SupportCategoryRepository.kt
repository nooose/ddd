package wolfdesk.tenant.domain.tenant

import org.springframework.data.jpa.repository.JpaRepository

interface SupportCategoryRepository : JpaRepository<SupportCategory, Long> {

    fun findByNameAndTenantId(name: String, tenantId: Long): SupportCategory?
}
