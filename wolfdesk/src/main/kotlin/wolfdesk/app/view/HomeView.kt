package wolfdesk.app.view

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@PageTitle("울프데스크")
@Route("/", layout = BaseLayout::class)
class HomeView : VerticalLayout() {
    init {
        add("안녕하세요.${System.lineSeparator()}울프데스크입니다.")
        add(createTicketButton())
    }

    private fun createTicketButton(): Button {
        return createPrimaryButton("로그인") {
            UI.getCurrent().navigate("/login")
        }
    }
}
