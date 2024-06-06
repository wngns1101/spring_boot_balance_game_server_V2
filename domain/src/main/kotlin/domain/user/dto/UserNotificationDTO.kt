package domain.user.dto

import domain.user.entity.UserNotification
import domain.user.model.UserNotificationType

data class UserNotificationDTO(
    val userNotificationId: Long,
    val userId: Long,
    val type: UserNotificationType,
    val status: Boolean
)

fun UserNotification.toDTO(): UserNotificationDTO {
    return UserNotificationDTO(
        userNotificationId = userNotificationId!!,
        userId = userId,
        type = type,
        status = status,
    )
}
