package wolfdesk.member.application.command

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class MemberCommand(
    @field:NotBlank
    val name: String,

    @field:NotBlank(message = "password must not be blank")
    @field:Pattern(
        regexp = PASSWORD_REGEX,
        message = "Password must be 10-24 characters long and contain at least one letter and one special character"
    )
    val password: String,

    @field:NotBlank
    @field:Email(message = "Email should be valid")
    val email: String,
) {

    companion object {
        const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*[!@#\$%^&*()_+{}:<>?])[A-Za-z\\d!@#\$%^&*()_+{}:<>?]{10,24}$"
    }
}
