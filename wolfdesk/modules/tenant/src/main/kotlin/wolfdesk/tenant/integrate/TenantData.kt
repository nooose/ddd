package wolfdesk.tenant.integrate

data class TenantData(
    val id: Long,
    val name: String,
    val supportCategories: List<Category>,
) {

    data class Category(
        val id: Long,
        val name: String,
    )
}
