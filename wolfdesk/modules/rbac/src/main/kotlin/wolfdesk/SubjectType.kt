package wolfdesk

enum class SubjectType(
    val value: String
) {
    MEMBER("member"),
    GROUP("group"),
    ;

    companion object {
        fun from(value: String): SubjectType {
            return SubjectType.entries.first { it.value == value }
        }
    }
}
