package wolfdesk.tenant.application

import jakarta.validation.constraints.NotBlank

data class TenantCreateCommand(
    @field:NotBlank
    val name: String,
)

data class CategoryCreateCommand(
    @field:NotBlank
    val name: String,
)
