package wolfdesk.tenant.domain.tenant

import jakarta.persistence.*

@Table(name = "support_category")
@Entity
class SupportCategory(
    @Column(nullable = false)
    val name: String,
    val tenantId: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
