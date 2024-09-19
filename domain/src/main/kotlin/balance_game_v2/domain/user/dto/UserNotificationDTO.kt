package balance_game_v2.domain.user.dto

import balance_game_v2.domain.user.entity.UserNotification
import balance_game_v2.domain.user.model.UserNotificationType

data class UserNotificationDTO(
    val userNotificationId: Long,
    val userId: Long,
    val type: UserNotificationType,
    val status: Boolean
)

fun UserNotification.toDTO(): balance_game_v2.domain.user.dto.UserNotificationDTO {
    return balance_game_v2.domain.user.dto.UserNotificationDTO(
        userNotificationId = userNotificationId!!,
        userId = userId,
        type = type,
        status = status,
    )
}
