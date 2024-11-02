package wolfdesk.member.ui

import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wolfdesk.base.security.MemberPrincipal
import wolfdesk.member.application.command.MemberCommand
import wolfdesk.member.application.command.MemberCommandService
import wolfdesk.member.application.query.MemberInfo
import wolfdesk.member.application.query.MemberQuery
import wolfdesk.member.application.query.MemberQueryService

@RestController
class AuthController(
    private val memberQueryService: MemberQueryService,
    private val memberCommandService: MemberCommandService,
) {
    // TODO: 토큰 만료되었을때 refresh 토큰을 받아 다시 만들어주는 api 작성(TokenController 만들기)
    @PostMapping("/members/token")
    fun login(@RequestBody @Valid query: MemberQuery, response: HttpServletResponse): ResponseEntity<Unit> {
        val token = memberQueryService.login(query)
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PostMapping("/members")
    fun register(@Valid command: MemberCommand) {
        memberCommandService.register(command)
    }

    @GetMapping("/members/me")
    fun getMember(@AuthenticationPrincipal principal: MemberPrincipal): MemberInfo {
        return memberQueryService.getMember(principal.memberId)
    }
}
