package balance_game_v2.api.v1.notification.http.req

import domain.user.dto.CreateUserNotificationCommand
import domain.user.model.UserNotificationStatus
import domain.user.model.UserNotificationType

data class CreateUserNotificationRequestDTO (
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    val status: UserNotificationStatus,
    val type: UserNotificationType
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