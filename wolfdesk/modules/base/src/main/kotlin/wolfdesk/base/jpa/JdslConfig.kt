package wolfdesk.base.jpa

import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutorImpl
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class JdslConfig(
    private val entityManager: EntityManager,
    private val renderContexts: List<RenderContext>,
) {

    @Bean
    fun jdslJpqlExecutor(): KotlinJdslJpqlExecutorImpl {
        val renderContext = renderContexts.reversed().reduce { acc, renderContext -> acc + renderContext }
        return KotlinJdslJpqlExecutorImpl(entityManager, renderContext, null)
    }
}
