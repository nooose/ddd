package wolfdesk

import com.authzed.api.v1.ObjectReference
import com.authzed.api.v1.Relationship
import com.authzed.api.v1.RelationshipUpdate
import com.authzed.api.v1.SubjectReference

fun createResource(
    resource: ResourceType,
    id: String,
): ObjectReference {
    return ObjectReference.newBuilder()
        .setObjectType(resource.value)
        .setObjectId(id)
        .build()
}

fun createSubject(
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

fun createRelationShip(
    resource: ObjectReference,
    relation: Relation,
    subject: SubjectReference,
): Relationship {
    return Relationship.newBuilder()
        .setResource(resource)
        .setRelation(relation.value)
        .setSubject(subject)
        .build()
}

infix fun ObjectReference.relation(relation: Relation): Pair<ObjectReference, Relation> {
    return Pair(this, relation)
}

infix fun Pair<ObjectReference, Relation>.to(subject: SubjectReference): RelationshipUpdate {
    return RelationshipUpdate.newBuilder()
        .setRelationship(createRelationShip(this.first, this.second, subject))
        .setOperation(RelationshipUpdate.Operation.OPERATION_CREATE)
        .build()
}
