package wolfdesk.base.config

import org.hibernate.cfg.AvailableSettings
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class JpaConfig {

    @Bean
    fun functionLogCustomizer(): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer {
            it[AvailableSettings.STATEMENT_INSPECTOR] = CustomSqlCommenter()
        }
    }
}
