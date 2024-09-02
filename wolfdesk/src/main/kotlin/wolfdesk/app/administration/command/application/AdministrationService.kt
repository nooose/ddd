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

    fun tenantOnboarding(command: TenantCommand) {
        val tenant = Tenant(
            command.name,
            command.email,
            command.url
        )
        tenantRepository.save(tenant)

        if (command.category.isNotEmpty()) {
            val supportCategories = command.category.map {
                SupportCategory(name = it)
            }
            supportCategoryRepository.saveAll(supportCategories)
        }

        // todo: 테넌트 최초 등록 시 1명의 에이전트는 필수로 등록되며 보통 등록한 사람이 에이전트(ADMIN)가 되어야한다.
        val agent = Agent(
            tenantId = tenant.id,
            name = command.agent.name,
            position = Position.ADMIN
        )
        agentRepository.save(agent)
    }

    // todo: 에이전트의 역할이 동적으로 변경될 가능성이 커지면 바깥에서 Position 을 받아와서 저장하는걸로 변경해야한다.
    fun registerAgent(command: RegisterAgentCommand) {
        val agent = Agent(
            position = Position.STAFF,
            name = command.name,
            tenantId = command.tenantId
        )
        agentRepository.save(agent)
    }
}
