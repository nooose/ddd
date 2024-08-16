package wolfdesk.app

import org.springframework.beans.factory.InitializingBean
import org.springframework.jdbc.core.simple.JdbcClient

class DatabaseCleaner(
    private val jdbcClient: JdbcClient,
) : InitializingBean {

    private var tables: MutableList<String> = mutableListOf()
    private var truncateQueries: String = ""
    private var truncateRestartQueries: String = ""

    override fun afterPropertiesSet() {
        val tables = jdbcClient.sql("""
                SELECT TABLE_NAME
                FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'
            """).query(String::class.java).list()
        truncateQueries = tables.joinToString(";") { "TRUNCATE TABLE $it" }
        truncateRestartQueries = tables.joinToString(";") { "TRUNCATE TABLE $it RESTART IDENTITY" }
        this.tables = tables
    }

    fun truncateTables() {
        jdbcClient.sql("""
            SET REFERENTIAL_INTEGRITY FALSE;
            ${truncateQueries};
            ${truncateRestartQueries};
            SET REFERENTIAL_INTEGRITY TRUE;
        """).update()
    }
}
