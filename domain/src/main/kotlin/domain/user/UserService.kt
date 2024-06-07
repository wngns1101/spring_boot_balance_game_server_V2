package domain.user

import domain.auth.AuthService
import domain.auth.exception.NotFoundUserException
import domain.auth.model.AuthGroup
import domain.board.exception.NotFoundException
import domain.error.AlreadySignUpException
import domain.notification.repository.NotificationRepository
import domain.user.dto.JoinUserCommand
import domain.user.dto.PageUserNotificationDTO
import domain.user.dto.UserDTO
import domain.user.dto.UserNotificationDTO
import domain.user.dto.UserReportDTO
import domain.user.dto.toDTO
import domain.user.entity.TermsAgreementHistory
import domain.user.entity.User
import domain.user.entity.UserNotification
import domain.user.entity.UserReport
import domain.user.model.TermsAgreementHistoryType
import domain.user.model.UserNotificationType
import domain.user.repository.TermsAgreementHistoryRepository
import domain.user.repository.UserNotificationRepository
import domain.user.repository.UserReportRepository
import domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val notificationRepository: NotificationRepository,
    private val termsAgreementHistoryRepository: TermsAgreementHistoryRepository,
    private val userNotificationRepository: UserNotificationRepository,
    private val userReportRepository: UserReportRepository,
) {
    @Transactional
    fun signUp(email: String, password: String, joinUserCommand: JoinUserCommand): Pair<String, AuthGroup> {
        val authResult = authService.signUp(email, password)
        val userResult = userRepository.findByEmailAndDeletedAtIsNull(authResult.first)

        if (userResult != null) throw AlreadySignUpException()

        val user = User(
            email = joinUserCommand.email,
            nickname = joinUserCommand.nickname,
            realName = joinUserCommand.realName,
            birth = joinUserCommand.birth,
            phoneNumber = joinUserCommand.phoneNumber,
            invitationCode = "",
            pushToken = joinUserCommand.pushToken,
        )

        userRepository.save(user)

        val termsAgreementHistories = mutableListOf<TermsAgreementHistory>()

        termsAgreementHistories += TermsAgreementHistory(userId = user.userId!!, type = TermsAgreementHistoryType.SERVICE, status = true)
        termsAgreementHistories += TermsAgreementHistory(userId = user.userId!!, type = TermsAgreementHistoryType.MARKETING, status = joinUserCommand.isCheckedMarketing)

        termsAgreementHistoryRepository.saveAll(termsAgreementHistories)

        val userNotifications = mutableListOf<UserNotification>()

        userNotifications += UserNotification(userId = user.userId!!, type = UserNotificationType.SERVICE, status = true)
        userNotifications += UserNotification(userId = user.userId!!, type = UserNotificationType.MARKETING, status = joinUserCommand.isCheckedMarketing)

        userNotificationRepository.saveAll(userNotifications)

        return authResult
    }

    fun getUserByEmail(email: String): UserDTO {
        val user = userRepository.findByEmailAndDeletedAtIsNull(email)
        user.let { return user!!.toDTO() }
    }

    @Transactional
    fun modifyUserInfo(userId: Long, nickName: String, phoneNumber: String) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        user.nickname = nickName
        user.phoneNumber = phoneNumber
    }

    @Transactional
    fun withdraw(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }
        user.deletedAt = LocalDateTime.now()

//        TODO: 삭제 스케쥴러 추가
    }

    fun getUserById(userId: Long): UserDTO {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }
        return user.toDTO()
    }

    @Transactional
    fun updateUserPushToken(userId: Long, pushToken: String) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }
        user.pushToken = pushToken
    }

    fun getUserNotificationHistories(userId: Long, page: Int, size: Int): PageUserNotificationDTO {
        val pageable = PageRequest.of(page, size)

        val notifications = notificationRepository.findByUserId(userId, pageable)

        return PageUserNotificationDTO(
            notifications = notifications.content.map { it.toDTO() },
            totalPage = notifications.totalPages,
        )
    }

    @Transactional
    fun readUserNotification(userId: Long, notificationId: Long) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }
        val notification = notificationRepository.findById(notificationId).orElseThrow { NotFoundUserException() }
        notification.isRead = true
    }

    @Transactional
    fun modifyMarketingAgreement(userId: Long): Boolean {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        val marketingAgreement = termsAgreementHistoryRepository.findByUserIdAndType(user.userId!!, TermsAgreementHistoryType.MARKETING)
            ?: throw NotFoundException()
        termsAgreementHistoryRepository.delete(marketingAgreement)

        val newMarketingAgreement =
            TermsAgreementHistory(userId = userId, type = TermsAgreementHistoryType.MARKETING, status = !marketingAgreement.status)
        termsAgreementHistoryRepository.save(newMarketingAgreement)

        return newMarketingAgreement.status
    }

    fun getUserNotifications(userId: Long): List<UserNotificationDTO> {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        return userNotificationRepository.findAllByUserId(user.userId!!).map { it.toDTO() }
    }

    @Transactional
    fun modifyUserNotifications(userId: Long, userNotificationId: Long): Boolean {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        val userNotification = userNotificationRepository.findById(userNotificationId).orElseThrow { NotFoundUserException() }
        userNotification.status = !userNotification.status

        return userNotification.status
    }

    fun getUserInvitation(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

//      TODO: 초대코드 생성
    }

    @Transactional
    fun createUserReport(userId: Long, targetUserId: Long) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        UserReport(
            userId = userId,
            targetUserId = targetUserId,
        ).let { userReportRepository.save(it) }
    }

    fun getUserReports(userId: Long): List<UserReportDTO> {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        return userReportRepository.findAllByUserId(userId).map { it.toDTO() }
    }
}
