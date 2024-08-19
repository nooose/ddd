package wolfdesk.app.config

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutorImpl
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class JdslRepository(
    private val jdslJpqlExecutor: KotlinJdslJpqlExecutorImpl,
    private val jdslRenderContext: RenderContext,
    private val entityManager: EntityManager,
) {

    fun <T : Any> findAll(init: Jpql.() -> JpqlQueryable<SelectQuery<T>>): List<T> {
        CurrentFunNameHolder.funName = Thread.currentThread().callerName
        return jdslJpqlExecutor.findAll { init() }
            .filterNotNull()
    }

    fun <T : Any> findOne(init: Jpql.() -> JpqlQueryable<SelectQuery<T>>): T? {
        CurrentFunNameHolder.funName = Thread.currentThread().callerName
        return try {
            entityManager.createQuery(init(Jpql()).toQuery(), jdslRenderContext)
                .apply { maxResults = 1 }
                .singleResult
        } catch (e: Exception) {
            null
        }
    }

    fun <T : Any> findPage(pageable: Pageable, init: Jpql.() -> JpqlQueryable<SelectQuery<T>>): Page<T> {
        CurrentFunNameHolder.funName = Thread.currentThread().callerName
        val page = jdslJpqlExecutor.findPage(pageable) { init() }
        return PageImpl(page.filterNotNull(), pageable, page.totalElements)
    }

    private val Thread.callerName: String
        get() {
            val stack = this.stackTrace[3]
            return "${stack.className}-${stack.methodName}"
        }
}
