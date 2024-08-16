package wolfdesk.app

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.simple.JdbcClient

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
}
