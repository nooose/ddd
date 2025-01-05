package wolfdesk

import com.authzed.api.v1.CheckPermissionRequest
import com.authzed.api.v1.CheckPermissionResponse.Permissionship.PERMISSIONSHIP_HAS_PERMISSION
import com.authzed.api.v1.Consistency
import com.authzed.api.v1.LookupSubjectsRequest
import com.authzed.api.v1.PermissionsServiceGrpc
import com.authzed.api.v1.WriteRelationshipsRequest
import org.springframework.stereotype.Component

@Component
class RbacClient(
    private val client: PermissionsServiceGrpc.PermissionsServiceBlockingStub,
) {

    fun hasPermission(resource: ResourceType, resourceId: String, subject: SubjectType, subjectId: String, permission: Permission): Boolean {
        val consistency = Consistency.newBuilder()
            .setMinimizeLatency(true)
            .build()

        val request = CheckPermissionRequest.newBuilder()
            .setConsistency(consistency)
            .setResource(createResource(resource, resourceId))
            .setSubject(createSubject(subject, subjectId))
            .setPermission(permission.value)
            .build()

        val response = client.checkPermission(request)
        return response.permissionship == PERMISSIONSHIP_HAS_PERMISSION
    }

    fun getAccessibleList(resource: ResourceType, resourceId: String, subject: SubjectType, permission: Permission): List<SubjectResponse> {
        val request = LookupSubjectsRequest.newBuilder()
            .setResource(createResource(resource, resourceId))
            .setSubjectObjectType(subject.value)
            .setPermission(permission.value)
            .build()

        return client.lookupSubjects(request).asSequence()
            .map {
                SubjectResponse(
                    subject = subject,
                    id = it.subject.subjectObjectId,
                )
        }.toList()
    }

    fun relation(resource: ResourceType, resourceId: String, requestRelation: Relation, subject: SubjectType, subjectId: String) {
        val resource = createResource(resource, resourceId)
        val subject = createSubject(subject, subjectId)

        val operation = resource relation requestRelation to subject
        val request= WriteRelationshipsRequest.newBuilder()
            .addAllUpdates(listOf(operation))
            .build()
        client.writeRelationships(request)
    }
}
