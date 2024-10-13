package wolfdesk.base.event

data class TenantSystemEvent(
    val tenantId: Long,
    val type: Type,
) : SystemEvent() {
    enum class Type {
        CREATED,
        DELETED,
        UPDATED,
    }
}
