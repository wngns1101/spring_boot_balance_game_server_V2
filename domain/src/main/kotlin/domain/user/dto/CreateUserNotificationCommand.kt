package domain.user.dto

import domain.user.model.UserNotificationStatus
import domain.user.model.UserNotificationType

data class CreateUserNotificationCommand (
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    val status: UserNotificationStatus,
    val type: UserNotificationType,
)