package wolfdesk.tenant.domain.invitation

import org.springframework.data.jpa.repository.JpaRepository

interface InvitationRepository : JpaRepository<Invitation, Long>  {
}
