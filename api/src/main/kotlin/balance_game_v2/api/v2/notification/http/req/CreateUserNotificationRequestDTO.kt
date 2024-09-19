package balance_game_v2.api.v2.notification.http.req

import balance_game_v2.domain.notification.model.NotificationStatus
import balance_game_v2.domain.notification.model.NotificationType
import balance_game_v2.domain.user.dto.CreateUserNotificationCommand

data class CreateUserNotificationRequestDTO(
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    val status: NotificationStatus,
    val type: NotificationType,
)

fun CreateUserNotificationRequestDTO.toCommand(): CreateUserNotificationCommand {
    return CreateUserNotificationCommand(
        title = title,
        body = body,
        imageUrl = imageUrl,
        link = link,
        status = status,
        type = type,
    )
}
