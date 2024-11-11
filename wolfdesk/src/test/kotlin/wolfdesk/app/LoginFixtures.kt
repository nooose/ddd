package wolfdesk.app

import wolfdesk.base.security.Password
import wolfdesk.member.application.command.MemberJoinCommand
import wolfdesk.member.application.query.MemberLoginCommand

fun createJoinCommandFixture(
    name: String = "김영철",
    email: String = "test@test.com",
    password: String = "1234"
): MemberJoinCommand {
    return MemberJoinCommand(
        name = name,
        password = Password(password),
        email = email
    )
}

fun createLoginCommandFixture(
    email: String = "test@test.com",
    password: String = "1234"
): MemberLoginCommand {
    return MemberLoginCommand(
        email = email,
        password = Password(password)
    )
}
