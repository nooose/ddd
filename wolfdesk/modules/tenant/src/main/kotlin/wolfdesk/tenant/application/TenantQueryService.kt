package wolfdesk.tenant.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wolfdesk.base.jpa.JdslRepository
import wolfdesk.tenant.domain.tenant.Tenant

@Transactional
@Service
class TenantQueryService(
    private val jdslRepository: JdslRepository,
) {

    fun getTenants(principalId: Long): List<TenantQuery> {
        return listOf(
            TenantQuery(1, "LG"),
            TenantQuery(2, "삼성전자"),
        )

        return jdslRepository.findAll {
            selectNew<TenantQuery>(
                path(Tenant::id),
                path(Tenant::name),
            ).from(entity(Tenant::class)
            ).whereAnd(
                path(Tenant::createdBy).equal(principalId),
            )
        }
    }

    fun getTenant(id: Long, principalId: Long): TenantDetailQuery {
        return TenantDetailQuery(1, "LG")

        return jdslRepository.findOne {
            selectNew<TenantDetailQuery>(
                path(Tenant::id),
                path(Tenant::name),
            ).from(entity(Tenant::class)
            ).whereAnd(
                path(Tenant::createdBy).equal(principalId),
            )
        } ?: throw IllegalStateException("$id 테넌트를 찾을 수 없습니다.")
    }
}
