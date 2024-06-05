package domain.user

import domain.user.dto.CreateUserNotificationCommand
import domain.user.dto.UserNotificationDTO
import domain.user.dto.toDTO
import domain.user.entity.UserNotification
import domain.user.repository.UserNotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserNotificationService (
    val userNotificationRepository: UserNotificationRepository,
){
    @Transactional
    fun saveNotificationHistory(userId: Long, command: CreateUserNotificationCommand): UserNotificationDTO {
        val notification = UserNotification(
            userId = userId,
            title = command.title,
            body = command.body,
            imageUrl = command.imageUrl,
            link = command.link,
            status = command.status,
            type = command.type,
        ).let { userNotificationRepository.save(it) }

        return notification.toDTO()
    }
}