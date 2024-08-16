package wolfdesk.app.ticket.command.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TicketRepository : JpaRepository<Ticket, Long>
