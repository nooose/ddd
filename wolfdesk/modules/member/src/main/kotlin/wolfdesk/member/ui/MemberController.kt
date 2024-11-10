package wolfdesk.member.ui

import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wolfdesk.base.security.MemberPrincipal
import wolfdesk.member.application.command.MemberJoinCommand
import wolfdesk.member.application.command.MemberJoinService
import wolfdesk.member.application.query.MemberInfo
import wolfdesk.member.application.query.MemberQueryService

@RestController
class MemberController(
    private val memberQueryService: MemberQueryService,
    private val memberJoinService: MemberJoinService,
) {

    @PostMapping("/members")
    fun register(@RequestBody @Valid command: MemberJoinCommand) {
        memberJoinService.register(command)
    }

    @GetMapping("/members/me")
    fun getMember(@AuthenticationPrincipal principal: MemberPrincipal): MemberInfo {
        return memberQueryService.getMember(principal.memberId)
    }
}
