package wolfdesk.app.view

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.datetimepicker.DateTimePicker
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import wolfdesk.ticket.query.TicketQuery
import wolfdesk.ticket.query.TicketQueryService

@PageTitle("울프데스크")
@Route("/tickets", layout = BaseLayout::class)
class TicketSingleView(
    private val ticketQueryService: TicketQueryService,
) : VerticalLayout(), HasUrlParameter<Long> {
    private var id = 0L
    private val title = TextField("제목").apply { isReadOnly = true }
    private val description = TextArea("내용").apply { isReadOnly = true }
    private val createdAt = DateTimePicker("작성 시간").apply { isReadOnly = true }

    override fun setParameter(event: BeforeEvent, parameter: Long) {
        this.id = parameter

        try {
            val ticket = ticketQueryService.getOne(id)
            bindTicket(ticket)
            add(title, description, createdAt, createdBackButton())
        } catch (e: Exception) {
            createNotification(e.message ?: "")
            UI.getCurrent().navigate(TicketView::class.java)
        }
    }

    private fun bindTicket(ticket: TicketQuery) {
        title.value = ticket.title
        description.value = ticket.description
        createdAt.value = ticket.createdAt
    }

    private fun createdBackButton(): Button {
        return createSuccessButton("확인") {
            UI.getCurrent().navigate(TicketView::class.java)
        }
    }
}
