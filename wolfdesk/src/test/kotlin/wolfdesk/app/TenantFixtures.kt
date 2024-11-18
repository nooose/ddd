package wolfdesk.app

import wolfdesk.tenant.application.CategoryCreateCommand
import wolfdesk.tenant.application.TenantCreateCommand

fun createTenantCommandFixture(
    name: String,
): TenantCreateCommand {
    return TenantCreateCommand(name)
}

fun createCategoryCommandFixture(
    name: String,
): CategoryCreateCommand {
    return CategoryCreateCommand(name)
}
