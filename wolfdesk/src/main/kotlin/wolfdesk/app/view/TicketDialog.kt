package wolfdesk.app.view

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import wolfdesk.ticket.command.application.TicketCreateCommand
import wolfdesk.ticket.command.application.TicketService

class TicketDialog(
    private val ticketService: TicketService,
    reloadComponents: () -> Unit
) : Dialog() {
    private val form = TicketForm()

    init {
        add(form, createButton(reloadComponents))
        open()
    }

    private fun createButton(reloadComponents: () -> Unit): Button {
        return createPrimaryButton("저장") {
            form.bindOrNull()?.let {
                val command = TicketCreateCommand(it.title, it.description, 1)
                ticketService.create(command, 1)
                close()
                reloadComponents()
            }
        }
    }
}
