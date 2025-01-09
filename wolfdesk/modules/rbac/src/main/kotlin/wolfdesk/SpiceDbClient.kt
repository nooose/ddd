package wolfdesk

import com.authzed.api.v1.CheckPermissionRequest
import com.authzed.api.v1.CheckPermissionResponse.Permissionship.PERMISSIONSHIP_HAS_PERMISSION
import com.authzed.api.v1.Consistency
import com.authzed.api.v1.LookupResourcesRequest
import com.authzed.api.v1.LookupSubjectsRequest
import com.authzed.api.v1.ObjectReference
import com.authzed.api.v1.PermissionsServiceGrpc
import com.authzed.api.v1.Relationship
import com.authzed.api.v1.RelationshipUpdate
import com.authzed.api.v1.SubjectReference
import com.authzed.api.v1.WriteRelationshipsRequest
import com.authzed.api.v1.ZedToken
import org.springframework.stereotype.Component

@Component
class SpiceDbClient(
    private val client: PermissionsServiceGrpc.PermissionsServiceBlockingStub,
) : RbacClient {

    override fun hasPermission(
        resource: RbacResource,
        subject: RbacSubject,
        permission: Permission,
    ): Boolean {
        val consistency = Consistency.newBuilder()
            .setMinimizeLatency(true)
            .setAtLeastAsFresh(ZedToken.newBuilder()
            )
            .build()

        val request = CheckPermissionRequest.newBuilder()
            .setConsistency(consistency)
            .setResource(createResource(resource.type, resource.id))
            .setSubject(createSubject(subject.type, subject.id))
            .setPermission(permission.value)
            .build()

        val response = client.checkPermission(request)
        return response.permissionship == PERMISSIONSHIP_HAS_PERMISSION
    }

    override fun getAccessibleSubjects(
        resource: RbacResource,
        subjectType: SubjectType,
        permission: Permission
    ): List<SubjectResponse> {
        val request = LookupSubjectsRequest.newBuilder()
            .setResource(createResource(resource.type, resource.id))
            .setSubjectObjectType(subjectType.value)
            .setPermission(permission.value)
            .build()

        return client.lookupSubjects(request).asSequence()
            .map {
                SubjectResponse(
                    id = it.subject.subjectObjectId,
                    type = subjectType,
                )
            }.toList()
    }

    override fun getAccessibleResources(
        subject: RbacSubject,
        resourceType: ResourceType,
        permission: Permission
    ): List<ResourceResponse> {
        val request = LookupResourcesRequest.newBuilder()
            .setSubject(createSubject(subject.type, subject.id))
            .setResourceObjectType(resourceType.value)
            .setPermission(permission.value)
            .build()

        return client.lookupResources(request).asSequence()
            .map {
                ResourceResponse(
                    id = it.resourceObjectId,
                    type = resourceType,
                )
            }.toList()
    }

    override fun setRelationShip(
        resource: RbacResource,
        relation: RelationType,
        subject: RbacSubject,
    ) {
        val updateRelation = toUpdateRelationShip(resource, relation, subject)
        val relationshipRequest= WriteRelationshipsRequest.newBuilder()
            .addUpdates(updateRelation)
            .build()
        client.writeRelationships(relationshipRequest)
    }

    override fun setRelationShips(
        requests: List<RelationShipRequest>,
    ) {
        val updates = requests.map { toUpdateRelationShip(it.resource, it.relation, it.subject) }
        val relationshipRequest = WriteRelationshipsRequest.newBuilder()
            .addAllUpdates(updates)
            .build()

        client.writeRelationships(relationshipRequest)
    }

    private fun toUpdateRelationShip(
        resource: RbacResource,
        relation: RelationType,
        subject: RbacSubject,
    ): RelationshipUpdate {
        val requestResource = createResource(resource.type, resource.id)
        val requestSubject = createSubject(subject.type, subject.id)
        return RelationshipUpdate.newBuilder()
            .setRelationship(createRelationShip(requestResource, relation, requestSubject))
            .setOperation(RelationshipUpdate.Operation.OPERATION_CREATE)
            .build()
    }

    private fun createResource(
        resource: ResourceType,
        id: String,
    ): ObjectReference {
        return ObjectReference.newBuilder()
            .setObjectType(resource.value)
            .setObjectId(id)
            .build()
    }

    private fun createSubject(
        subject: SubjectType,
        id: String,
    ): SubjectReference {
        return SubjectReference.newBuilder()
            .setObject(
                ObjectReference.newBuilder()
                    .setObjectType(subject.value)
                    .setObjectId(id)
                    .build()
            ).build()
    }

    private fun createRelationShip(
        resource: ObjectReference,
        relation: RelationType,
        subject: SubjectReference,
    ): Relationship {
        return Relationship.newBuilder()
            .setResource(resource)
            .setRelation(relation.value)
            .setSubject(subject)
            .build()
    }

}
