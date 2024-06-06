package domain.user.dto

import domain.notification.model.NotificationStatus
import domain.notification.model.NotificationType

data class CreateUserNotificationCommand(
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    val status: NotificationStatus,
    val type: NotificationType,
)
