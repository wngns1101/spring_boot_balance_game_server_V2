package domain.notification.repository

import domain.notification.entity.Notification
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findByUserId(userId: Long, pageRequest: PageRequest): Page<Notification>
}
