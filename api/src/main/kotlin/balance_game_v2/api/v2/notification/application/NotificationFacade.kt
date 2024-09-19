package balance_game_v2.api.v2.notification.application

import balance_game_v2.api.v2.notification.http.req.CreateUserNotificationRequestDTO
import balance_game_v2.api.v2.notification.http.req.toCommand
import balance_game_v2.domain.notification.FcmClient
import balance_game_v2.domain.notification.NotificationService
import balance_game_v2.domain.user.UserService
import org.springframework.stereotype.Component

@Component
class NotificationFacade(
    val notificationService: NotificationService,
    val userService: UserService,
    val fcmClient: FcmClient,
) {
    fun send(userId: Long, request: CreateUserNotificationRequestDTO) {
        val notification = notificationService.saveNotificationHistory(userId, request.toCommand())
        val user = userService.getUserById(userId)
        if (user.pushToken != null) {
            fcmClient.send(user.pushToken!!, notification.title, notification.body, notification.link)
        }
    }
}
