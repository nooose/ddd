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
import wolfdesk.support.views.createPrimaryButton

@PageTitle("울프데스크")
@Route("/tickets", layout = BaseLayout::class)
class TicketView(
    private val ticketService: TicketService
) : VerticalLayout() {

    init {
        val grid = Grid(TicketDto::class.java, false)
        grid.addColumn(TicketDto::title).setHeader("티켓 제목")

        // TODO: 티켓 목록 출력
        val tickets = listOf(
            TicketDto("버그있어요"),
            TicketDto("테스트"),
            TicketDto("로그인 문의")
        )
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
