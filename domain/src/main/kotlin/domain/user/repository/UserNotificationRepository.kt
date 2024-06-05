package domain.user.repository

import domain.user.entity.UserNotification
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserNotificationRepository : JpaRepository<UserNotification, Long> {
    fun findByUserId(userId: Long, pageRequest: PageRequest): Page<UserNotification>
}