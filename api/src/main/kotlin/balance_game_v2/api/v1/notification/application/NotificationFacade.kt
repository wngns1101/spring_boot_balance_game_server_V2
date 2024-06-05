package balance_game_v2.api.v1.notification.application

import balance_game_v2.api.v1.notification.http.req.CreateUserNotificationRequestDTO
import balance_game_v2.api.v1.notification.http.req.toCommand
import domain.notification.FcmClient
import domain.user.UserNotificationService
import domain.user.UserService
import org.springframework.stereotype.Component

@Component
class NotificationFacade(
    val userNotificationService: UserNotificationService,
    val userService: UserService,
    val fcmClient: FcmClient,
) {
    fun send(userId: Long, request: CreateUserNotificationRequestDTO) {
        val notification = userNotificationService.saveNotificationHistory(userId, request.toCommand())
        val user = userService.getUserById(userId)
        if (user.pushToken != null) {
            fcmClient.send(user.pushToken!!, notification.title, notification.body, notification.link)
        }
    }
}
