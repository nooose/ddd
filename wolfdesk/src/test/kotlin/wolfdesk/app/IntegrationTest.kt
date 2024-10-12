package wolfdesk.app

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@Import(IntegrationTestConfig::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ComponentScan(basePackages = [
    "wolf.base",
    "wolf.ticket",
])
@SpringBootTest
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntegrationTest
