package wolfdesk.app.administration.command.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.app.administration.domain.*

@Transactional
@Service
class AdministrationService(
    private val tenantRepository: TenantRepository,
    private val supportCategoryRepository: SupportCategoryRepository,
    private val agentRepository: AgentRepository,
) {

    fun tenantOnboarding(command: TenantCommand): Long {
        val tenant = Tenant(
            command.name,
            command.email,
            command.url
        )
        val supportCategories = command.category.map { SupportCategory(name = it, tenantId = tenant.id) }

        // TODO: 테넌트 최초 등록 시 1명의 에이전트는 필수로 등록되며 보통 등록한 사람이 에이전트(ADMIN)가 되어야한다.
        val agent = Agent.admin(
            tenantId = tenant.id,
            name = "김영철", // FIXME: 이름 하드코딩 제거
        )

        tenantRepository.save(tenant)
        supportCategoryRepository.saveAll(supportCategories)
        agentRepository.save(agent)
        return tenant.id
    }

    // TODO: 에이전트의 역할이 동적으로 변경될 가능성이 커지면 바깥에서 Position 을 받아와서 저장하는걸로 변경해야한다.
    fun registerAgent(command: RegisterAgentCommand) {
        val agent = Agent.staff(
            name = command.name,
            tenantId = command.tenantId
        )
        agentRepository.save(agent)
    }
}
