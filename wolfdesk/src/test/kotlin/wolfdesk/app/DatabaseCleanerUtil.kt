package wolfdesk.app

object DatabaseCleanerUtil {
    lateinit var databaseCleaner: DatabaseCleaner

    fun truncate() {
        databaseCleaner.truncateTables()
        println("Database truncated")
    }
}
