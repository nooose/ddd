package wolfdesk.app.member.acceptance

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import wolfdesk.app.*

@DisplayName("인증 인수 테스트")
@AcceptanceTest
class AuthAcceptanceTest(
    @LocalServerPort
    private val port: Int,
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    beforeSpec {
        initRestAssured(port)
    }

    Given("가입을 하고") {
        val joinCommand = createJoinCommandFixture(email = "test@test.com")
        가입(joinCommand) status HttpStatus.OK

        When("로그인을 하면") {
            val loginCommand = createLoginCommandFixture(email = "test@test.com")
            val response = 로그인(loginCommand) status HttpStatus.OK

            Then("토큰을 발급받을 수 있다") {
                val token = response.getString("data.accessToken")
                token shouldNotBe null
            }
        }
    }

    afterContainer {
        DatabaseCleanerUtil.truncate()
    }
})
