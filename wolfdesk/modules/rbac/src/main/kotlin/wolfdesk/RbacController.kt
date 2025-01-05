package wolfdesk

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wolfdesk.base.api.ApiResponse

@RestController
internal class RbacController(
    private val rbacClient: RbacClient,
) {

    @GetMapping("/permit")
    fun hasPermission(
        @RequestParam resource: String,
        @RequestParam resourceId: String,
        @RequestParam subject: String,
        @RequestParam subjectId: String,
        @RequestParam permission: String,
    ): ApiResponse<Boolean> {
        val response = rbacClient.hasPermission(
            ResourceType.from(resource),
            resourceId,
            SubjectType.from(subject),
            subjectId,
            Permission.from(permission),
        )
        return ApiResponse.success(response)
    }

    @GetMapping("/subjects")
    fun getAccessibleList(
        @RequestParam resource: String,
        @RequestParam resourceId: String,
        @RequestParam subject: String,
        @RequestParam permission: String,
    ): ApiResponse<List<SubjectResponse>> {
        val responses = rbacClient.getAccessibleList(
            ResourceType.from(resource),
            resourceId,
            SubjectType.from(subject),
            Permission.from(permission),
        )
        return ApiResponse.success(responses)
    }
}
