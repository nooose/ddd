package wolfdesk.app.administration.domain

import org.springframework.data.jpa.repository.JpaRepository

interface AgentRepository : JpaRepository<Agent, Long> {
    fun findAllByTenantId(tenantId: Long): List<Agent>
}
