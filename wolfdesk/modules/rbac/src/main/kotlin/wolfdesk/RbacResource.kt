package wolfdesk

data class RbacResource(
    val id: String,
    val type: ResourceType
)

data class RbacSubject(
    val id: String,
    val type: SubjectType
)
