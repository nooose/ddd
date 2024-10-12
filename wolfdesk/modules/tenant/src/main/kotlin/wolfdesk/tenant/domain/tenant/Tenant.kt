package wolfdesk.tenant.domain.tenant

import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot
import java.time.LocalDateTime

@Table(name = "tenant")
@Entity
class Tenant(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val createdBy: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) : AbstractAggregateRoot<Tenant>() {

    init {
        registerEvent(TenantCreatedEvent(id, createdBy))
    }

    fun validateOwner(createdBy: Long) {
        check(this.createdBy == createdBy) { "$createdBy 테넌트 관리자가 아닙니다." }
    }
}
