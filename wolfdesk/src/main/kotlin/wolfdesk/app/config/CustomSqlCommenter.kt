package wolfdesk.app.config

import org.hibernate.resource.jdbc.spi.StatementInspector


class CustomSqlCommenter : StatementInspector {
    override fun inspect(sql: String): String {
        val currentFunction = CurrentFunNameHolder.funName
        CurrentFunNameHolder.clear()
        if (currentFunction.isNullOrBlank()) {
            return sql
        }
        return "/* $currentFunction */ $sql"
    }
}
