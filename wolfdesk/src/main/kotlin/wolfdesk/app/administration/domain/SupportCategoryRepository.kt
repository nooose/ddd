package wolfdesk.app.administration.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SupportCategoryRepository : JpaRepository<SupportCategory, Long> {
}
