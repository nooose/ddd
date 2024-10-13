package wolfdesk.app.view

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route

@Route("/login", layout = BaseLayout::class)
class LoginView : VerticalLayout() {
    private val email: TextField = TextField("아이디(이메일)")
    private val password: TextField = TextField("비밀번호")

    init {
        add("로그인")
        add(email, password)
        add(createButtonLayout()) // 수평 버튼 레이아웃 추가
    }

    private fun createButtonLayout(): HorizontalLayout {
        val buttonLayout = HorizontalLayout()
        buttonLayout.add(createLoginButton(), createJoinButton()) // 버튼 추가
        return buttonLayout
    }

    private fun createLoginButton(): Button {
        return createPrimaryButton("로그인") {
            // 로그인 버튼 클릭 시 동작
        }
    }

    private fun createJoinButton(): Button {
        return createPrimaryButton("회원가입") {
            UI.getCurrent().navigate("/join")
        }
    }
}
