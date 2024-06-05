package domain.user.dto

data class PageUserNotificationDTO (
    val notifications: List<UserNotificationDTO>,
    val totalPage: Int,
)