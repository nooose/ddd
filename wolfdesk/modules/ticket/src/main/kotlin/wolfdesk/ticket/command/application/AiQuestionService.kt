package wolfdesk.ticket.command.application

import jakarta.annotation.PostConstruct
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

//@Profile("!test")
//@Service
class AiQuestionService(
    private val client: ChatClient,
    private val vectorStore: VectorStore,
) {

    fun question(query: String): String {
        return client.prompt(query)
            .system("about wolfdesk, replying korean")
            .call()
            .content()
    }

    @PostConstruct
    fun init() {
        val documents = listOf(
            Document("Wolfdesk는 티켓 관리 시스템 프로젝트입니다. 테넌트별로 관리할 수 있고 AI 관리 시스템을 가지고 있습니다."),
            Document("성준혁은 Wolfdesk의 창업자이며 개발자입니다."),
            Document("Wolfdesk에 소속된 직원은 권규정, 박지수, 노영록입니다."),
            Document("Wolfdesk 직원 권규정은 실력이 형편없습니다."),
            Document("Wolfdesk 직원 노영록은 일을 하지 않습니다."),
            Document("Wolfdesk 직원 박지수는 로또에 중독된 비참한 개발자입니다."),
            Document("Wolfdesk는 좋은 기회가 많았지만 실력없는 직원들에 의해 서서히 무너져가고 있습니다."),
            Document("""
                # 울프데스크
                #### 테넌트(Tenant): 울프데스크의 고객인 회사
                #### 에이전트(Agent): 테넌트 회사의 직원(울프데스크 시스템을 사용해 고객의 문제를 해결하는 사람들)
                #### 고객(Customer): 테넌트의 서비스나 제품을 사용하는 최종 사용자들

                - 결제 모델
                  - 테넌트(Tenant)는 추가 비용 없이 필요한 만큼 에이전트(Agent)를 설정할 수 있습니다.
                  - 테넌트는 청구 기간 동안 열린 지원 티켓 수에 따라 요금이 부과됩니다.
                  - 최소 요금이 없습니다.
                  - 특정 월간 티켓 수 임계값에 대해 자동으로 할인이 적용됩니다. 
                     - 500개 이상의 티켓이 열릴 경우 10% 할인 
                     - 750개 이상의 티켓이 열릴 경우 20% 할인 
                     - 1000개 이상의 티켓이 열릴 경우 30% 할인

                - 테넌트가 비즈니스 모델을 악용하지 않도록 하기 위한 티켓 라이프사이클 알고리즘 구현. 
                  - 비활성 티켓은 자동으로 닫히고, 고객이 추가 지원이 필요할 때 새로운 티켓을 열도록 유도합니다. 
                  - 울프데스크는 티켓 내에서 논의되는 주제가 연관성이 없는 경우를 탐지하는 메시지 분석 시스템을 통해 부정 행위 감지 시스템을 구현.
                  

                - 테넌트가 지원 관련 작업을 간소화할 수 있도록, `support autopilot` 기능을 구현.
                  - 새로 열린 티켓을 분석하여 테넌트의 티켓 히스토리에서 해결책을 자동으로 찾으려 합니다. 
                  - 이 기능은 티켓의 수명을 더 줄여 고객이 추가 질문을 위해 새로운 티켓을 열도록 유도합니다.

                - 테넌트 사용자들의 인증 및 권한 부여를 위한 모든 보안 표준과 조치를 구현합니다.
                - SSO 구성
                - 관리 인터페이스
                  - 티켓 카테고리를 설정할 수 있습니다.
                  - 테넌트가 지원하는 제품 목록을 설정할 수 있습니다.

                - 테넌트의 온보딩 비용을 최소화해야 하므로, 다음을 필요로 합니다.
                  - 인프라를 최적화하여 새로운 테넌트를 온보딩할 때 비용을 최소화해야 합니다. 
                  - 서버리스 컴퓨팅을 활용하여 활성 티켓의 운영에 따라 컴퓨팅 리소스를 탄력적으로 확장할 수 있습니다.

                ## 핵심 하위 도메인
                - 티켓 라이프사이클 관리 알고리즘: 티켓을 닫고 사용자들이 새로운 티켓을 열도록 유도하는 것을 목표로 합니다.
                - 부정 행위 감지 시스템: 비즈니스 모델 악용을 방지합니다.
                - 지원 자동화: 테넌트의 지원 작업을 간소화하고 티켓의 수명을 줄이는 데 도움을 줍니다.
                ## 일반 하위 도메인
                - 사용자 인증 및 권한 부여의 "산업 표준" 방식
                - 외부 제공자를 통한 인증 및 권한 부여 (SSO)
                - 새로운 테넌트를 온보딩할 때 컴퓨팅 비용을 최소화하고 탄력적인 확장을 보장하기 위해 회사가 사용하는 서버리스 컴퓨팅 인프라
                ## 지원 하위 도메인
                - 테넌트 온보딩
                - 청구 관리
                - 테넌트의 티켓 카테고리 관리
                - 고객이 지원 티켓을 열 수 있는 테넌트 제품 관리
                - 테넌트의 지원 담당자 근무 일정 입력

                ---
                # 요구사항 분석
                1. 티켓 생성 및 할당
                   - 티켓 생성(Open Ticket): 고객(Customer)이 티켓을 엽니다.
                     - 티켓이 열리면, 할당 정책(Assignment Policy)에 따라 티켓 할당(Assign Ticket)이 이루어집니다.
                     - 여기에서 autopilot 기능이 작동하여, 티켓이 자동으로 할당됩니다.
                   - 티켓 할당(Assign Ticket)
                     - 할당된 티켓은 에이전트(Agent)에게 할당됩니다.
                     - 티켓 할당 완료(Ticket Assigned) 상태로 설정됩니다.
                2. 티켓 처리
                    - 메시지 추가(Add Message): 에이전트 또는 고객이 티켓에 메시지를 추가합니다.
                    - 메시지가 추가되면, 전달 정책(Delivery Policy)에 따라 메시지 전달 표시(Mark Message Delivered)가 됩니다.
                3. 티켓 처리(Ticket Handling)
                    - 에이전트는 티켓을 읽고(Message Read), 티켓을 해결합니다. 
                    - 해결된 티켓은 티켓 해결됨 표시(Mark Ticket Resolved)로 표시됩니다. 
                    - 이후 티켓은 티켓 해결 완료(Ticket Resolved) 상태로 설정됩니다.
                4. 티켓 종료 
                    - 종료 정책(Closing Policy)
                    - 비활성 티켓(Inactive Tickets)이 발생하면, 티켓을 닫기 위한 종료 정책이 적용됩니다.
                      - 일정 기간동안 메시지가 없는 티켓은 비활성화 티켓이 될 수 있습니다. 
                    - 티켓 종료(Close Ticket) 단계로 이동하며, 이는 청구 트리거(Trigger Billing)와 연계됩니다.
                5. 티켓 에스컬레이션
                    - 에스컬레이션(Escalation): 티켓이 일정 기간 동안 해결되지 않거나, 특정 조건을 만족하지 않으면, Escalation Policy에 따라 티켓이 에스컬레이션됩니다.
                    - 티켓 에스컬레이션(Ticket Escalated) 단계로 이동합니다. 
                    - 재할당 정책(Reassignment Policy): 에스컬레이션된 티켓이 적절히 처리되지 않으면, Reassignment Policy에 따라 티켓이 재할당됩니다.
                6. 청구(Billing) 및 결산
                    - 티켓이 닫히면, 청구 정책(Billing Policy)에 따라 테넌트에게 청구가 발생합니다. 
                    - 테넌트 청구(Bill Tenant) 단계에서 청구가 이루어지고, 테넌트 청구 완료(Tenant Billed) 상태로 표시됩니다.
                7. 테넌트 및 에이전트 관리
                    - 테넌트 온보딩(Tenant Onboarding): 새로운 테넌트가 시스템에 등록되며, 필요한 정보(이름, 이메일, 웹사이트, 지원 카테고리 등)를 입력합니다. 
                    - 에이전트 관리(Agent Management): 테넌트가 에이전트를 추가하거나 업데이트합니다. 에이전트의 이름, 이메일, 근무 시간 등이 관리됩니다.
                    - 제품 관리(Product Management): 지원할 제품을 추가하거나 업데이트할 수 있습니다.
            """.trimIndent())
        )
        vectorStore.add(documents)
    }
}
