package balance_game_v2.api.v1.user.application

import balance_game_v2.api.v1.user.http.exception.ExpiredTokenException
import balance_game_v2.api.v1.user.http.exception.InvalidTokenTypeException
import balance_game_v2.api.v1.user.http.exception.NotEqualsTokenException
import balance_game_v2.api.v1.user.http.exception.UnknownException
import balance_game_v2.api.v1.user.http.req.SignUpCommand
import domain.auth.AuthService
import domain.board.BoardService
import domain.board.exception.NotFoundException
import domain.user.UserService
import domain.user.dto.PageUserNotificationDTO
import domain.user.dto.UserDTO
import domain.user.dto.UserNotificationDTO
import domain.user.dto.UserReportDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import redis.certificate.entity.Certificate
import redis.certificate.repository.CertificateRepository

@Component
class UserFacade(
    private val userService: UserService,
    private val authService: AuthService,
    private val tokenManager: TokenManager,
    private val boardService: BoardService,
    private val mailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,
    @Value("\${spring.mail.username}")
    private val targetEmail: String,
    private val certificateRepository: CertificateRepository,
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

    fun getUserByAccountName(email: String): UserDTO {
        return userService.getUserByEmail(email)
    }

    fun changeUserPassword(email: String, currentPassword: String, newPassword: String) {
        authService.changeUserPassword(email, currentPassword, newPassword)
    }

    fun modifyUserInfo(userId: Long, nickname: String, profileUrl: String?) {
        userService.modifyUserInfo(userId, nickname, profileUrl)
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

//    fun getBoardReports(userId: Long): List<BoardReportDTO> {
//        return boardService.getBoardReports(userId)
//    }
//
//    fun getBoardCommentReports(userId: Long): List<BoardCommentReportDTO> {
//        return boardService.getBoardCommentReports(userId)
//    }

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

    @Async
    fun sendAuthCodeForEmailCertificate(email: String, code: String) {
        val context = Context()
        context.setVariable("authCode", code)

        val mailBody = templateEngine.process(EmailMetaData.TEMPLATE, context)

        val message = mailSender.createMimeMessage()

        val helper = MimeMessageHelper(message)
        helper.setTo(email) // 수신자 email
        helper.setSubject(EmailMetaData.CERTIFICATE_EMAIL_TITLE)
        helper.setText(mailBody, true)
        helper.setFrom(targetEmail)
        mailSender.send(message)

        Certificate(
            email = email,
            code = code,
        ).let {
            certificateRepository.save(it)
        }
    }

    fun checkEmailCertificate(email: String, code: String): Boolean {
        val existCertificate = certificateRepository.findById(email).orElseThrow { NotFoundException() }

        return existCertificate.code == code
    }
}
