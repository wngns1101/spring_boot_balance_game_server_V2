package balance_game_v2.api.v2.user.application

import balance_game_v2.api.v2.user.http.exception.ExpiredTokenException
import balance_game_v2.api.v2.user.http.exception.InvalidTokenTypeException
import balance_game_v2.api.v2.user.http.exception.NotEqualsTokenException
import balance_game_v2.api.v2.user.http.exception.UnknownException
import balance_game_v2.api.v2.user.http.req.SignUpCommand
import balance_game_v2.domain.auth.AuthService
import balance_game_v2.domain.auth.BlockReasonDTO
import balance_game_v2.domain.board.BoardService
import balance_game_v2.domain.board.exception.NotFoundException
import balance_game_v2.domain.user.UserService
import balance_game_v2.domain.user.dto.PageUserNotificationDTO
import balance_game_v2.domain.user.dto.UserDTO
import balance_game_v2.domain.user.dto.WithDrawCommandDTO
import balance_game_v2.redis.certificate.entity.Certificate
import balance_game_v2.redis.certificate.repository.CertificateRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

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

    fun signIn(email: String, password: String, pushToken: String?): SignInDTO {
        val auth = authService.signIn(email, password)

        if (pushToken != null) {
            val user = userService.getUserByAccountName(email)
            userService.updateUserPushToken(user.userId, pushToken)
        }

        val tokens = tokenManager.makeJwtToken(auth.accountName, auth.authGroup)
        authService.updateToken(auth.accountName, tokens.refreshToken)

        return SignInDTO(
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken,
            status = auth.status
        )
    }

    fun getUserByAccountName(accountName: String): UserDTO {
        return userService.getUserByAccountName(accountName)
    }

    fun changeUserPassword(email: String, currentPassword: String, newPassword: String) {
        authService.changeUserPassword(email, currentPassword, newPassword)
    }

    fun modifyUserInfo(userId: Long, nickname: String, profileUrl: String?) {
        userService.modifyUserInfo(userId, nickname, profileUrl)
    }

    fun withdraw(userId: Long, accountName: String, command: WithDrawCommandDTO) {
        authService.withdraw(accountName)
        userService.withdraw(userId, command)
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

    fun getUserNotifications(userId: Long): List<balance_game_v2.domain.user.dto.UserNotificationDTO> {
        return userService.getUserNotifications(userId)
    }

    fun modifyUserNotification(userId: Long, userNotificationId: Long): Boolean {
        return userService.modifyUserNotifications(userId, userNotificationId)
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

    @Async
    fun sendAuthCodeForEmailCertificate(email: String, code: String) {
        val context = Context()
        context.setVariable("authCode", code)

        val mailBody = templateEngine.process(EmailMetaData.AUTH_CODE_TEMPLATE, context)

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

    fun checkTokenValidation(accountName: String) {
        authService.checkTokenValidation(accountName)
    }

    fun checkBlockReason(userId: Long): BlockReasonDTO {
        return authService.checkBlockReason(userId)
    }

    fun getUserByEmail(email: String): UserDTO {
        return userService.getUserByEmail(email)
    }

    fun sendAccountNameForEmail(email: String, accountName: String) {
        val context = Context()
        context.setVariable("accountName", accountName)

        val mailBody = templateEngine.process(EmailMetaData.ACCOUNT_NAME_TEMPLATE, context)

        val message = mailSender.createMimeMessage()

        val helper = MimeMessageHelper(message)
        helper.setTo(email) // 수신자 email
        helper.setSubject(EmailMetaData.ACCOUNT_NAME_AUTH_CODE_TITLE)
        helper.setText(mailBody, true)
        helper.setFrom(targetEmail)
        mailSender.send(message)
    }

    fun sendTemporaryPasswordForAccountName(accountName: String, email: String) {
        val getTemporaryPassword = getTemporaryPassword()
        val encodePw = authService.convertPassword(getTemporaryPassword)

        val context = Context()
        context.setVariable("temporaryPassword", getTemporaryPassword)

        val mailBody = templateEngine.process(EmailMetaData.TEMPORARY_PASSWORD_TEMPLATE, context)

        val message = mailSender.createMimeMessage()

        val helper = MimeMessageHelper(message)
        helper.setTo(email) // 수신자 email
        helper.setSubject(EmailMetaData.TEMPORARY_PASSWORD_TITLE)
        helper.setText(mailBody, true)
        helper.setFrom(targetEmail)
        mailSender.send(message)

        authService.updatePassword(accountName, encodePw)
    }

    private final fun getTemporaryPassword(): String {
        val charSet: List<Char> = listOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        )
        var str = ""
        repeat(10) {
            str += charSet.random()
        }
        return str
    }
}
