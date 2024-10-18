package wolfdesk.tenant.application

data class TenantQuery(
    val id: Long,
    val name: String,
)

data class TenantDetailQuery(
    val id: Long,
    val name: String,
)
