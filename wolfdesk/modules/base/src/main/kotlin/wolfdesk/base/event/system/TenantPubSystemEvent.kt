package wolfdesk.base.event.system

data class TenantPubSystemEvent(
    val tenantId: Long,
    val type: Type,
) : PubSystemEvent() {
    enum class Type {
        CREATED,
        DELETED,
        UPDATED,
    }
}
