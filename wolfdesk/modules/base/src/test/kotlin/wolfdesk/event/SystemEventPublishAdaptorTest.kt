package wolfdesk.event

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import wolfdesk.base.event.EventPublishAdaptor
import wolfdesk.base.event.system.SystemEvent

class SystemEventPublishAdaptorTest : StringSpec({
    "시스템 이벤트로 변환할 수 있다." {
        val adaptor = TestEventPublishAdaptor()

        val event = TestEvent("test")

        adaptor.supports(event) shouldBe true
        adaptor.convert(event) shouldBe TestSystemEvent("converted")
    }
})

class TestEventPublishAdaptor : EventPublishAdaptor<TestEvent, TestSystemEvent> {

    override fun convert(event: TestEvent): TestSystemEvent {
        return TestSystemEvent("converted")
    }

    override fun supports(event: Any): Boolean {
        return event is TestEvent
    }
}

data class TestEvent(
    val name: String,
)

data class TestSystemEvent(
    val name: String,
) : SystemEvent()
