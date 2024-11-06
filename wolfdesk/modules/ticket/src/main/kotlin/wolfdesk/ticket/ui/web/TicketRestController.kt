package wolfdesk.ticket.ui.web

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri
import wolfdesk.base.api.ApiResponse
import wolfdesk.ticket.command.application.MessageCreateCommand
import wolfdesk.ticket.command.application.TicketCreateCommand
import wolfdesk.ticket.command.application.TicketService
import wolfdesk.ticket.query.TicketQuery
import wolfdesk.ticket.query.TicketQueryService
import wolfdesk.ticket.query.TicketSimpleQuery
import java.util.*

@RestController
class TicketRestController(
    private val ticketService: TicketService,
    private val ticketQueryService: TicketQueryService,
) {

    @PostMapping("/tickets")
    fun createTicket(@RequestBody @Valid command: TicketCreateCommand): ResponseEntity<ApiResponse<Unit>> {
        val ticketId = ticketService.create(command, 1)
        val uri = fromCurrentRequestUri().path("/$ticketId").build().toUri()
        val body = ApiResponse.success<Unit>()
        return ResponseEntity.created(uri).body(body)
    }

    @PostMapping("/tickets/{ticketId}/open")
    fun openTicket(@PathVariable ticketId: Long): ApiResponse<Unit> {
        ticketService.open(ticketId, 1)
        return ApiResponse.success()
    }

    @PostMapping("/tickets/{ticketId}/messages")
    fun addMessage(
        @PathVariable("ticketId") ticketId: Long,
        @RequestBody @Valid command: MessageCreateCommand
    ): ApiResponse<Unit> {
        ticketService.addMessage(ticketId, command, 1)
        return ApiResponse.success()
    }

    @DeleteMapping("/tickets/{ticketId}/messages/{messageId}")
    fun addMessage(
        @PathVariable("ticketId") ticketId: Long,
        @PathVariable("messageId") messageId: UUID,
    ): ApiResponse<Unit> {
        ticketService.deleteMessage(ticketId, messageId = messageId, 1)
        return ApiResponse.success()
    }

    @GetMapping("/tickets/{ticketId}")
    fun getTickets(
        @PathVariable("ticketId") ticketId: Long,
    ): ApiResponse<TicketQuery> {
        val response = ticketQueryService.getOne(ticketId)
        return ApiResponse.success(response)
    }

    @GetMapping("/tickets")
    fun getTickets(): ApiResponse<List<TicketSimpleQuery>> {
        val response = ticketQueryService.getAll()
        return ApiResponse.success(response)
    }
}
