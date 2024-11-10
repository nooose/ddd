package wolfdesk.tenant.integrate

import org.springframework.stereotype.Service

@Service
class TenantIntegrationService {

    fun getTenant(id: Long): TenantData {
        return TenantData(
            id = 1L,
            name = "테넌트A",
            supportCategories = listOf(
                TenantData.Category(
                    id = 1L,
                    name = "계약",
                ),
                TenantData.Category(
                    id = 2L,
                    name = "계정",
                )
            )
        )
    }

    fun getTenantsByMemberId(memberId: Long): List<TenantData> {
        return listOf(
            TenantData(
                id = 1L,
                name = "테넌트A",
                supportCategories = listOf(
                    TenantData.Category(
                        id = 1L,
                        name = "계약",
                    ),
                    TenantData.Category(
                        id = 2L,
                        name = "계정",
                    )
                )
            ),
            TenantData(
                id = 2L,
                name = "테넌트B",
                supportCategories = listOf(
                    TenantData.Category(
                        id = 3L,
                        name = "컴퓨터",
                    ),
                    TenantData.Category(
                        id = 4L,
                        name = "냉장고",
                    )
                )
            )
        )
    }
}
