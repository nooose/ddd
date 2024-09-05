package wolfdesk.app.administration.command.application

import wolfdesk.app.administration.domain.Position


data class TenantCommand(
    val name: String,
    val email: String,
    val url: String,
    val category: List<String> = emptyList(),
)

data class RegisterAgentCommand(
    val name: String,
    val position: Position = Position.ADMIN,
    val tenantId: Long,
)
