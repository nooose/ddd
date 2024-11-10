package wolfdesk.member.ui

import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wolfdesk.base.api.ApiResponse
import wolfdesk.base.security.TokenResponse
import wolfdesk.member.application.command.MemberLoginService
import wolfdesk.member.application.query.MemberLoginCommand

@RestController
class AuthController(
    private val memberLoginService: MemberLoginService,
) {

    @PostMapping("/auth/token")
    fun login(@RequestBody @Valid command: MemberLoginCommand, response: HttpServletResponse): ApiResponse<TokenResponse> {
        return ApiResponse.success(memberLoginService.login(command))
    }
}
