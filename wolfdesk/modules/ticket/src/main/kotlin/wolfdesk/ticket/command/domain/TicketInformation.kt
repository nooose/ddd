package wolfdesk.ticket.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

@Embeddable
data class TicketInformation(
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false, columnDefinition = "TEXT")
    val description: String,
    @Column(nullable = false)
    val supportCategoryId: Long,
    @Column(nullable = false)
    val createdById: Long,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val priority: Priority = Priority.LOW,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val state: State = State.NONE,
    val closedById: Long? = null,
    val agentId: Long? = null,
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    fun isOwner(createdById: Long): Boolean {
        return this.createdById == createdById
    }

    val isOpened
        get() = state == State.OPEN
}
