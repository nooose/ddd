package wolfdesk.app

import org.springframework.data.jpa.repository.JpaRepository

object DatabaseCleanerUtil {
    lateinit var databaseCleaner: DatabaseCleaner
}

fun JpaRepository<*, *>.truncate() {
    return DatabaseCleanerUtil.databaseCleaner.truncateTables()
}
