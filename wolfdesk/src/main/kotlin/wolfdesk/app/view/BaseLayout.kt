package wolfdesk.app.view

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.Scroller
import com.vaadin.flow.component.sidenav.SideNav
import com.vaadin.flow.component.sidenav.SideNavItem
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.theme.lumo.LumoUtility


class BaseLayout : AppLayout() {
    init {
        primarySection = Section.DRAWER
        addToDrawer(createTitle(), createScroll())
        addToNavbar(DrawerToggle())
    }

    private fun createTitle(): Component {
        return RouterLink("", HomeView::class.java).apply {
            val title = H1("울프데스크").apply {
                style.set("font-size", "var(--lumo-font-size-l)")
                    .set("line-height", "var(--lumo-size-l)")
                    .set("margin", "0 var(--lumo-space-m)")
            }
            add(title)
        }
    }

    private fun createScroll(): Component {
        val nav = SideNav().apply {
            addItem(
                SideNavItem("티켓", TicketView::class.java)
            )
        }

        val scroller = Scroller(nav)
        scroller.className = LumoUtility.Padding.SMALL
        return scroller
    }
}

