package wolfdesk.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ConfigurationPropertiesScan(
    basePackages = [
        "wolfdesk.base"
    ]
)
@EntityScan(
    basePackages = [
        "wolfdesk.agent.domain",
        "wolfdesk.tenant.domain",
        "wolfdesk.ticket.command.domain",
    ]
)
@EnableJpaRepositories(
    basePackages = [
        "wolfdesk.member",
        "wolfdesk.tenant",
        "wolfdesk.ticket.command",
    ]
)
@SpringBootApplication(
    scanBasePackages = [
        "wolfdesk.base",
        "wolfdesk.member",
        "wolfdesk.tenant",
        "wolfdesk.ticket",
    ]
)
class WolfdeskApplication

fun main(args: Array<String>) {
    runApplication<WolfdeskApplication>(*args)
}
