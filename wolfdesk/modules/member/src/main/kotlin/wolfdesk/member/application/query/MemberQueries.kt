package wolfdesk.member.application.query

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import wolfdesk.base.security.Password

data class MemberLoginCommand(
    @field:Email
    @field:NotBlank
    val email: String,

    val password: Password,
)

data class MemberInfo(
    val name: String,
)
