package balance_game_v2.domain.user.dto

data class PageUserNotificationDTO(
    val notifications: List<NotificationDTO>,
    val totalPage: Int,
)
