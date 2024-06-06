package domain.notification

import domain.user.dto.CreateUserNotificationCommand
import domain.user.dto.NotificationDTO
import domain.user.dto.toDTO
import domain.notification.entity.Notification
import domain.notification.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService (
    val notificationRepository: NotificationRepository,
){
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