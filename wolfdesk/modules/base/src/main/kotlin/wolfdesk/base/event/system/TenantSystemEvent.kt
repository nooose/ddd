package wolfdesk.base.event.system

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
