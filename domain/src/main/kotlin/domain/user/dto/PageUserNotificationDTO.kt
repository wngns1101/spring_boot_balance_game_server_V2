package domain.user.dto

data class PageUserNotificationDTO (
    val notifications: List<NotificationDTO>,
    val totalPage: Int,
)