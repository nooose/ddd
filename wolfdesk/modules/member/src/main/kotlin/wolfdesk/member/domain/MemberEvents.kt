package wolfdesk.member.domain

data class TenantAgentConfirmedEvent(
    val tenantId: Long,
    val memberId: Long,
)

data class TenantCustomerConfirmedEvent(
    val tenantId: Long,
    val memberId: Long,
)
