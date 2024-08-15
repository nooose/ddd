package wolfdesk.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WolfdeskApplication

fun main(args: Array<String>) {
    runApplication<WolfdeskApplication>(*args)
}
