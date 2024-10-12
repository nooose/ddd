package wolfdesk.tenant.domain.invitation

import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot

@Entity
class Invitation(
    @Column(nullable = false)
    val tenantId: Long,
    @Column(nullable = false)
    val inviteeId: Long,
    @Enumerated(EnumType.STRING)
    val type: InvitationType,

    var status: InvitationStatus = InvitationStatus.SENT,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : AbstractAggregateRoot<Invitation>() {

    init {
        registerEvent(InvitedEvent(id, tenantId, inviteeId, type))
    }

    fun changeStatus(status: InvitationStatus, inviteeId: Long) {
        check(this.inviteeId == inviteeId) { "초대 받은자만 변경할 수 있습니다." }
        check(status == InvitationStatus.SENT) { "초대 상태를 변경할 수 없습니다." }
        this.status = status
    }
}
