package domain.user.repository

import domain.user.entity.UserNotification
import org.springframework.data.jpa.repository.JpaRepository

interface UserNotificationRepository : JpaRepository<UserNotification, Long> {
    fun findAllByUserId(userId: Long): List<UserNotification>
    fun deleteByUserId(userId: Long)
}
