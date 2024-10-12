package wolfdesk.tenant.domain.tenant

data class TenantCreatedEvent(
    val id: Long,
    val createdBy: Long,
)
