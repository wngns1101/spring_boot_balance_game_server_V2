package balance_game_v2.domain.user.dto

import balance_game_v2.domain.notification.model.NotificationStatus
import balance_game_v2.domain.notification.model.NotificationType

data class CreateUserNotificationCommand(
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    val status: NotificationStatus,
    val type: NotificationType,
)
