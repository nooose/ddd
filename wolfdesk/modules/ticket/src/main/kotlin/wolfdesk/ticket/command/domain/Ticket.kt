package wolfdesk.ticket.command.domain

import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot
import java.util.*

@Table(name = "ticket")
@Entity
class Ticket(
    val information: TicketInformation,
    messages: List<Message> = emptyList(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : AbstractAggregateRoot<Ticket>() {

    init {
        registerEvent(TicketCreatedEvent(this))
    }

    @JoinColumn(name = "ticket_id", nullable = false, updatable = false)
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    val messages: MutableList<Message> = messages.toMutableList()

    fun add(message: Message) {
        messages.add(message)
        registerEvent(MessageAddedEvent(message))
    }

    fun deleteMessage(messageId: UUID, messageCreatedById: Long) {
        val message = messages.first { it.id == messageId }
        message.validateOwner(messageCreatedById)
        messages.remove(message)
        registerEvent(MessageDeletedEvent(messageId))
    }

    fun assign(agentId: Long) {
        information.agentId = agentId
        registerEvent(TicketAssignedEvent(id, agentId))
    }

    fun open(createdById: Long) {
        check(information.isOwner(createdById)) { "티켓의 생성자가 오픈할 수 있습니다." }
        information.state = State.OPEN
        registerEvent(TicketOpenedEvent(this.id))
    }
}
