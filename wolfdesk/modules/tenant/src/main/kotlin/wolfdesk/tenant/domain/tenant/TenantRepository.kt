package wolfdesk.tenant.domain.tenant

import org.springframework.data.jpa.repository.JpaRepository

interface TenantRepository : JpaRepository<Tenant, Long> {

    fun findByCreatedBy(createdBy: Long): Tenant?
    fun findByName(name: String): Tenant?
}
