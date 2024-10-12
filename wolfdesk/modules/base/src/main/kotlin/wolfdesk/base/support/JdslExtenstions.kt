package wolfdesk.base.support

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entityable

fun Jpql.selectFrom(entity: Entityable<*>): SelectQueryWhereStep<Any> {
    return select(entity).from(entity)
}
