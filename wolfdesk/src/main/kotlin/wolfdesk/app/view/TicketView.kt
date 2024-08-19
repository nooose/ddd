package wolfdesk.app.view

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import wolfdesk.app.ticket.command.application.TicketService
import wolfdesk.app.ticket.query.TicketQuery
import wolfdesk.app.ticket.query.TicketQueryService
import wolfdesk.support.views.createPrimaryButton

@PageTitle("울프데스크")
@Route("/tickets", layout = BaseLayout::class)
class TicketView(
        private val ticketService: TicketService,
        private val ticketQueryService: TicketQueryService,
) : VerticalLayout() {

    init {
        val grid = Grid(TicketQuery::class.java, false)
        grid.addColumn(TicketQuery::id).setHeader("ID")
        grid.addColumn(TicketQuery::title).setHeader("제목")
        grid.addColumn(TicketQuery::createdAt).setHeader("생성시간")

        val tickets = ticketQueryService.getAll()
        grid.setItems(tickets)
        grid.isAllRowsVisible = true

        add(grid, createTicketButton())
    }

    private fun createTicketButton(): Button {
        return createPrimaryButton("티켓 생성") {
            openTicketDialog()
        }
    }

    private fun openTicketDialog() {
        TicketDialog(ticketService) {
            UI.getCurrent().page.reload()
        }
    }
}

data class TicketDto(
    @field:Pattern(
        regexp = "[a-z0-9가-힣]+",
        flags = [Pattern.Flag.CASE_INSENSITIVE],
    )
    val title: String = "",
    @field:NotBlank
    val description: String = "",
)
