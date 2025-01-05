package wolfdesk

enum class ResourceType(
    val value: String
) {
    TICKET("ticket"),
    ;

    companion object {
        fun from(value: String): ResourceType {
            return ResourceType.entries.first { it.value == value }
        }
    }
}
