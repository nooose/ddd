package wolfdesk.app.view

import BindingFormLayout
import com.vaadin.flow.component.textfield.TextField

class TicketForm : BindingFormLayout<TicketDto>(TicketDto::class) {
    private val title: TextField = TextField("제목")
    private val description: TextField = TextField("내용")

    init {
        add(title, description)
        setResponsiveSteps(ResponsiveStep("0", 1))
    }

    override fun bindOrNull(): TicketDto? {
        return bindDefaultOrNull()?.let {
            TicketDto(title.value, description.value)
        }
    }

    override fun fill(data: TicketDto) {
        fillDefault(data)
    }
}
