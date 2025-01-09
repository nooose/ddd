package wolfdesk

data class RelationShipRequest(
    val resource: RbacResource,
    val relation: RelationType,
    val subject: RbacSubject,
)
