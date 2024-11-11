package wolfdesk.member.application.command

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import wolfdesk.base.security.Password

data class MemberJoinCommand(
    @field:NotBlank
    val name: String,

    val password: Password,

    @field:NotBlank
    @field:Email(message = "Email should be valid")
    val email: String,
)

data class MemberLoginCommand(
    @field:Email
    @field:NotBlank
    val email: String,

    val password: Password,
)
