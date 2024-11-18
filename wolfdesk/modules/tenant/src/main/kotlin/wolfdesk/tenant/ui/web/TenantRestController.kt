package wolfdesk.tenant.ui.web

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import wolfdesk.base.api.ApiResponse
import wolfdesk.base.security.MemberPrincipal
import wolfdesk.tenant.application.CategoryCreateCommand
import wolfdesk.tenant.application.SupportCategoryService
import wolfdesk.tenant.application.TenantCreateCommand
import wolfdesk.tenant.application.TenantService

@RestController
class TenantRestController(
    private val tenantService: TenantService,
    private val supportCategoryService: SupportCategoryService,
) {

    @PostMapping("/tenants")
    fun createTenant(
        @RequestBody command: TenantCreateCommand,
        @AuthenticationPrincipal principal: MemberPrincipal,
    ): ResponseEntity<ApiResponse<Unit>> {
        val id = tenantService.createTenant(command, principal.memberId)
        val uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/$id")
            .build()
            .toUri()
        return ResponseEntity.created(uri).body(ApiResponse.success())
    }

    @PostMapping("/tenants/{tenantId}/categories")
    fun addSupportedCategory(
        @PathVariable tenantId: Long,
        @RequestBody command: CategoryCreateCommand,
        @AuthenticationPrincipal principal: MemberPrincipal,
    ): ApiResponse<Unit> {
        supportCategoryService.addSupportCategory(tenantId, command, principal.memberId)
        return ApiResponse.success()
    }
}
