package balance_game_v2.api.v1.user.application

import balance_game_v2.api.v1.user.http.req.SignUpCommand
import domain.auth.AuthService
import domain.user.UserService
import domain.user.dto.PageUserNotificationDTO
import domain.user.dto.UserDTO
import domain.user.dto.UserNotificationDTO
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userService: UserService,
    private val authService: AuthService,
    private val tokenManager: TokenManager,
) {
    fun signUp(userCommand: SignUpCommand): TokenDTO {
        val authResult = userService.signUp(userCommand.email, userCommand.password, userCommand.joinUserCommand)

        return tokenManager.makeJwtToken(authResult.first, authResult.second)
    }

    fun signIn(email: String, password: String, pushToken: String?): TokenDTO {
        val auth = authService.signIn(email, password)

        if (pushToken != null) {
            val user = userService.getUserByEmail(email)
            userService.updateUserPushToken(user.userId, pushToken)
        }

        return tokenManager.makeJwtToken(auth.first, auth.second)
    }

    fun getUserByEmail(email: String): UserDTO {
        return userService.getUserByEmail(email)
    }

    fun changeUserPassword(email: String, currentPassword: String, newPassword: String) {
        authService.changeUserPassword(email, currentPassword, newPassword)
    }

    fun modifyUserInfo(userId: Long, nickname: String, phoneNumber: String) {
        userService.modifyUserInfo(userId, nickname, phoneNumber)
    }

    fun withdraw(userId: Long) {
        userService.withdraw(userId)
    }

    fun getUserNotificationHistories(userId: Long, page: Int, size: Int): PageUserNotificationDTO {
        return userService.getUserNotificationHistories(userId, page, size)
    }

    fun readUserNotification(userId: Long, notificationId: Long) {
        return userService.readUserNotification(userId, notificationId)
    }

    fun modifyMarketingAgreement(userId: Long): Boolean {
        return userService.modifyMarketingAgreement(userId)
    }

    fun getUserNotifications(userId: Long): List<UserNotificationDTO> {
        return userService.getUserNotifications(userId)
    }

    fun modifyUserNotification(userId: Long, userNotificationId: Long): Boolean {
        return userService.modifyUserNotifications(userId, userNotificationId)
    }

    fun getUserInvitation(userId: Long): Any {
        return userService.getUserInvitation(userId)
    }
}
