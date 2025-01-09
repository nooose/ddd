package wolfdesk

data class SubjectResponse(
    val id: String,
    val type: SubjectType,
)

data class ResourceResponse(
    val id: String,
    val type: ResourceType,
)
