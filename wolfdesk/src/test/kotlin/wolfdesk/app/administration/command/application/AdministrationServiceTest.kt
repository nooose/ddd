package wolfdesk.app.administration.command.application

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import wolfdesk.app.IntegrationTest
import wolfdesk.app.administration.domain.AgentRepository
import wolfdesk.app.administration.domain.Position
import wolfdesk.app.administration.domain.SupportCategoryRepository
import wolfdesk.app.administration.domain.TenantRepository
import wolfdesk.app.truncate

@IntegrationTest
class AdministrationServiceTest(
    private val service: AdministrationService,
    private val tenantRepository: TenantRepository,
    private val supportCategoryRepository: SupportCategoryRepository,
    private val agentRepository: AgentRepository,
) : DescribeSpec({

    describe("테넌트 온보딩 기능") {
        val categories = listOf("컴퓨터", "모니터")
        val tenant = TenantCommand(
            name = "A회사",
            email = "test@test.com",
            url = "www.test.com",
            categories
        )

        context("테넌트를 저장하면") {
            val tenantId = service.tenantOnboarding(tenant)

            it("조회할 수 있다.") {
                tenantRepository.findById(tenantId) shouldNotBe null
            }

            it ("카테고리가 저장된다.") {
                supportCategoryRepository.findAllByTenantId(tenantId) shouldHaveSize categories.size
            }

            it("기본 ADMIN 에이전트가 저장된다.") {
                val agents = agentRepository.findAllByTenantId(tenantId)
                agents shouldHaveSize 1
                agents[0].position shouldBeEqual Position.ADMIN
            }
        }
    }

    afterSpec {
        tenantRepository.truncate()
        agentRepository.truncate()
    }
})

