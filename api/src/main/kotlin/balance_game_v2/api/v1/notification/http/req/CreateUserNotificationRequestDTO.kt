package balance_game_v2.api.v1.notification.http.req

import domain.notification.model.NotificationStatus
import domain.notification.model.NotificationType
import domain.user.dto.CreateUserNotificationCommand

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
