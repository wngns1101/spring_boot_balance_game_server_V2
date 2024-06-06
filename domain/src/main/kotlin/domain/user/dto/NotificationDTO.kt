package domain.user.dto

import domain.notification.entity.Notification
import domain.notification.model.NotificationStatus
import domain.notification.model.NotificationType

data class NotificationDTO(
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    val status: NotificationStatus,
    val type: NotificationType,
    val isRead: Boolean,
)

fun Notification.toDTO(): NotificationDTO {
    return NotificationDTO(
        title = title,
        body = body,
        imageUrl = imageUrl,
        link = link,
        status = status,
        type = type,
        isRead = isRead,
    )
}
