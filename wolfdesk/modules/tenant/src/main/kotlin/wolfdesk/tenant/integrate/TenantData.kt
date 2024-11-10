package wolfdesk.tenant.integrate

import org.springframework.modulith.NamedInterface

@NamedInterface("tenant-integrate")
data class TenantData(
    val id: Long,
    val name: String,
    val supportCategories: List<Category>,
) {

    @NamedInterface("tenant-integrate")
    data class Category(
        val id: Long,
        val name: String,
    )
}
