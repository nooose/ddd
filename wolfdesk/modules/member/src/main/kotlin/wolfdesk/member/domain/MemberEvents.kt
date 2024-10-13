package wolfdesk.member.domain

import wolfdesk.base.event.DomainEvent

data class TenantAgentConfirmedEvent(
    val tenantId: Long,
    val memberId: Long,
) : DomainEvent() {
}

data class TenantCustomerConfirmedEvent(
    val tenantId: Long,
    val memberId: Long,
) : DomainEvent() {
}
