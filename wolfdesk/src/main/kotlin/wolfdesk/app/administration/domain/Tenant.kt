package wolfdesk.app.administration.domain

import jakarta.persistence.*

@Table(name = "tenant")
@Entity
class Tenant(
    @Column(nullable = false)
    val name: String,
    val email: String,
    val url: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
