package wolfdesk.base.event

data class TenantSystemEvent(
    val tenantId: Long,
    val type: String,
) : SystemEvent()
