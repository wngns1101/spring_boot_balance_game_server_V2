package balance_game_v2.domain.user.repository

import balance_game_v2.domain.user.entity.UserNotification
import org.springframework.data.jpa.repository.JpaRepository

interface UserNotificationRepository : JpaRepository<UserNotification, Long> {
    fun findAllByUserId(userId: Long): List<UserNotification>
    fun deleteByUserId(userId: Long)
}
