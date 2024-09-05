package wolfdesk.app.administration.domain

import jakarta.persistence.*

@Table(name = "support_category")
@Entity
class SupportCategory(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val tenantId: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
