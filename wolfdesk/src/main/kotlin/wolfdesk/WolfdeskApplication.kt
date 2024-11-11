package wolfdesk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication(
    exclude = [UserDetailsServiceAutoConfiguration::class]
)
class WolfdeskApplication

fun main(args: Array<String>) {
    runApplication<WolfdeskApplication>(*args)
}
