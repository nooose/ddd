package wolfdesk.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@EntityScan(
    basePackages = [
        "wolfdesk.ticket.command.domain",
    ]
)
@EnableJpaRepositories(
    basePackages = [
        "wolfdesk.ticket.command",
    ]
)
@SpringBootApplication(
    scanBasePackages = [
        "wolfdesk.ticket",
        "wolfdesk.base",
    ]
)
class WolfdeskApplication

fun main(args: Array<String>) {
    runApplication<WolfdeskApplication>(*args)
}
