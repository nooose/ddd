package wolfdesk.app

import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.simple.JdbcClient
import wolfdesk.ticket.command.application.AiQuestionService

@TestConfiguration
class IntegrationTestConfig(
    private val jdbcClient: JdbcClient,
) {

    @Bean
    fun eventRecords(): EventRecords {
        return EventRecords()
    }

    @Bean
    fun databaseCleaner(): DatabaseCleaner {
        val databaseCleaner = DatabaseCleaner(jdbcClient)
        DatabaseCleanerUtil.databaseCleaner = databaseCleaner
        return databaseCleaner
    }

    @Primary
    @Bean
    fun mockkAi(): AiQuestionService {
        val mock = mockk<AiQuestionService>(relaxed = true)
        every { mock.question(any()) } returns "Hello"
        return mock
    }
}
