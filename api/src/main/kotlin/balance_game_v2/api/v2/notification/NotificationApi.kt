package balance_game_v2.api.v2.notification

import balance_game_v2.api.v2.notification.application.NotificationFacade
import balance_game_v2.api.v2.notification.http.req.CreateUserNotificationRequestDTO
import balance_game_v2.api.v2.user.application.UserFacade
import balance_game_v2.client.NOTIFICATION_V2_PREFIX
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "알림")
@RestController
@RequestMapping(NOTIFICATION_V2_PREFIX)
class NotificationApi(
    private val notificationFacade: NotificationFacade,
    private val userFacade: UserFacade,
) {

    @Operation(method = "[알림-001] 테스트 알림 발송")
    @PostMapping("/test")
    fun testNotification(
        @RequestAttribute("accountName") email: String,
        @RequestBody request: CreateUserNotificationRequestDTO,
    ) {
        val user = userFacade.getUserByAccountName(email)
        notificationFacade.send(user.userId, request)
    }
}
