package wolfdesk

enum class Permission(
    val value: String
) {
    WRITE("write"),
    READ("read"),
    ;

    companion object {
        fun from(value: String): Permission {
            return Permission.entries.first { it.value == value }
        }
    }
}
