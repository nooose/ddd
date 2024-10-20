package wolfdesk.app

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestEnvironment

@Import(IntegrationTestConfig::class)
@TestEnvironment
@SpringBootTest
annotation class IntegrationTest

@Import(IntegrationTestConfig::class)
@TestEnvironment
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
annotation class AcceptanceTest
