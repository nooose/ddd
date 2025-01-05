package wolfdesk

enum class Relation(
    val value: String,
) {
    GROUP_HOST("host"),
    GROUP_PARENT("parent"),
    GROUP_MEMBER("member"),

    TICKET_OWNER("owner"),
    TICKET_GROUP("group"),
}
