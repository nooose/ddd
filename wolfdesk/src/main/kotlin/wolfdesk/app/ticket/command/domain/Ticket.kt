package wolfdesk.app.ticket.command.domain

import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot

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
        registerEvent(MessageCreatedEvent(message))
    }

    fun open() {
        information.state = State.OPEN
    }
}
