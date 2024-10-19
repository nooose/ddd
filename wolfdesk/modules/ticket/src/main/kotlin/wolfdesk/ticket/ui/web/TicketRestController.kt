package wolfdesk.ticket.ui.web

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wolfdesk.ticket.command.application.TicketCreateCommand
import wolfdesk.ticket.command.application.TicketService
import wolfdesk.ticket.query.TicketQueryService
import wolfdesk.ticket.query.TicketSimpleQuery

@RestController
class TicketRestController(
    private val ticketService: TicketService,
    private val ticketQueryService: TicketQueryService,
) {

    @PostMapping("/tickets")
    fun createTicket(@RequestBody @Valid command: TicketCreateCommand) {
        ticketService.create(command, 1)
    }

    @GetMapping("/tickets")
    fun getTickets(): List<TicketSimpleQuery> {
        return ticketQueryService.getAll()
    }
}
