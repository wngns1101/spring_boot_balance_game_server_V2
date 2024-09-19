package balance_game_v2.domain.user.dto

import balance_game_v2.domain.notification.entity.Notification
import balance_game_v2.domain.notification.model.NotificationStatus
import balance_game_v2.domain.notification.model.NotificationType

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
