package domain.user.dto

import domain.user.entity.UserNotification
import domain.user.model.UserNotificationStatus
import domain.user.model.UserNotificationType

data class UserNotificationDTO (
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    val status: UserNotificationStatus,
    val type: UserNotificationType,
    val isRead: Boolean,
)

fun UserNotification.toDTO(): UserNotificationDTO {
    return UserNotificationDTO(
        title = title,
        body = body,
        imageUrl = imageUrl,
        link = link,
        status = status,
        type = type,
        isRead = isRead,
    )
}