package balance_game_v2.api.v1.user.application

import balance_game_v2.api.v1.user.http.exception.ExpiredTokenException
import balance_game_v2.api.v1.user.http.exception.InvalidTokenTypeException
import balance_game_v2.api.v1.user.http.exception.NotEqualsTokenException
import balance_game_v2.api.v1.user.http.exception.UnknownException
import balance_game_v2.api.v1.user.http.req.SignUpCommand
import domain.auth.AuthService
import domain.board.BoardService
import domain.board.dto.BoardCommentReportDTO
import domain.board.dto.BoardReportDTO
import domain.user.UserService
import domain.user.dto.PageUserNotificationDTO
import domain.user.dto.UserDTO
import domain.user.dto.UserNotificationDTO
import domain.user.dto.UserReportDTO
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userService: UserService,
    private val authService: AuthService,
    private val tokenManager: TokenManager,
    private val boardService: BoardService,
) {
    fun signUp(userCommand: SignUpCommand): TokenDTO {
        val authResult = userService.signUp(userCommand.accountName, userCommand.password, userCommand.joinUserCommand)
        val tokens = tokenManager.makeJwtToken(authResult.first, authResult.second)

        authService.updateToken(authResult.first, tokens.refreshToken)

        return tokens
    }

    fun signIn(email: String, password: String, pushToken: String?): TokenDTO {
        val auth = authService.signIn(email, password)

        if (pushToken != null) {
            val user = userService.getUserByEmail(email)
            userService.updateUserPushToken(user.userId, pushToken)
        }

        val tokens = tokenManager.makeJwtToken(auth.first, auth.second)
        authService.updateToken(auth.first, tokens.refreshToken)

        return tokens
    }

    fun getUserByEmail(email: String): UserDTO {
        return userService.getUserByEmail(email)
    }

    fun changeUserPassword(email: String, currentPassword: String, newPassword: String) {
        authService.changeUserPassword(email, currentPassword, newPassword)
    }

    fun modifyUserInfo(userId: Long, nickname: String) {
        userService.modifyUserInfo(userId, nickname)
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

    fun createUserReport(userId: Long, targetUserId: Long) {
        return userService.createUserReport(userId, targetUserId)
    }

    fun getUserReports(userId: Long): List<UserReportDTO> {
        return userService.getUserReports(userId)
    }

    fun getBoardReports(userId: Long): List<BoardReportDTO> {
        return boardService.getBoardReports(userId)
    }

    fun getBoardCommentReports(userId: Long): List<BoardCommentReportDTO> {
        return boardService.getBoardCommentReports(userId)
    }

    fun reIssue(token: String): TokenDTO {
        try {
            if (tokenManager.validationRefreshToken(token)) {
                if (tokenManager.isTokenExpired(token)) {
                    throw ExpiredTokenException()
                }

                val email = tokenManager.getUserEmailFromToken(token)

                if (authService.validationToken(email, token)) {
                    val authGroup = tokenManager.getAuthGroupFromToken(token)
                    val tokens = tokenManager.makeJwtToken(email, authGroup)
                    authService.updateToken(email, tokens.refreshToken)
                    return tokens
                } else {
                    throw NotEqualsTokenException()
                }
            } else {
                throw InvalidTokenTypeException()
            }
        } catch (e: InvalidTokenTypeException) {
            throw UnknownException()
        }
    }

    fun checkDuplicateEmail(email: String): Boolean {
        return authService.checkDuplicateEmail(email)
    }
}
