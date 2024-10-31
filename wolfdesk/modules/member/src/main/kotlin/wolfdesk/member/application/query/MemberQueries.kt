package wolfdesk.member.application.query

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class MemberQuery(
    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String,
)

data class MemberInfo(
    val name: String,
)
