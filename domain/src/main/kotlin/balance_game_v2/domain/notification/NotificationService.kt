package balance_game_v2.domain.notification

import balance_game_v2.domain.notification.entity.Notification
import balance_game_v2.domain.notification.repository.NotificationRepository
import balance_game_v2.domain.user.dto.CreateUserNotificationCommand
import balance_game_v2.domain.user.dto.NotificationDTO
import balance_game_v2.domain.user.dto.toDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    val notificationRepository: NotificationRepository,
) {
    @Transactional
    fun saveNotificationHistory(userId: Long, command: CreateUserNotificationCommand): NotificationDTO {
        val notification = Notification(
            userId = userId,
            title = command.title,
            body = command.body,
            imageUrl = command.imageUrl,
            link = command.link,
            status = command.status,
            type = command.type,
        ).let { notificationRepository.save(it) }

        return notification.toDTO()
    }
}
