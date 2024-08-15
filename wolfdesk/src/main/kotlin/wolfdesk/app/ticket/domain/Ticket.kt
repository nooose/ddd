package wolfdesk.app.ticket.domain

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

    @JoinColumn(name = "ticket_id", nullable = false, updatable = false)
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, orphanRemoval = true)
    val messages: MutableList<Message> = messages.toMutableList()

    fun addMessage(message: Message) {
        check(information.isOpened) { "열린 티켓에만 메시지를 남길 수 있습니다." }
        messages.add(message)
    }

    fun open() {
        information.state = State.OPEN
    }
}
