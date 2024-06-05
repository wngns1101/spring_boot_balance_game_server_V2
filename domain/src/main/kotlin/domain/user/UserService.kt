package domain.user

import domain.auth.AuthService
import domain.auth.exception.NotFoundUserException
import domain.auth.model.AuthGroup
import domain.board.dto.PageBoardDTO
import domain.board.dto.toPageBoardDTO
import domain.error.AlreadySignUpException
import domain.user.dto.*
import domain.user.entity.User
import domain.user.repository.UserNotificationRepository
import domain.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService (
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val userNotificationRepository: UserNotificationRepository,
){
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

        return authResult
    }

    fun getUserByEmail(email: String): UserDTO {
        val user = userRepository.findByEmailAndDeletedAtIsNull(email)
        user.let { return user!!.toDTO() }
    }

    @Transactional
    fun modifyUserInfo(userId: Long, nickName: String, phoneNumber: String) {
        val user = userRepository.findById(userId).orElseThrow{ NotFoundUserException() }

        user.nickname = nickName
        user.phoneNumber = phoneNumber
    }

    @Transactional
    fun withdraw(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow{ NotFoundUserException() }
        user.deletedAt = LocalDateTime.now()

//        TODO: 삭제 스케쥴러 추가
    }

    fun getUserById(userId: Long): UserDTO {
        val user = userRepository.findById(userId).orElseThrow{ NotFoundUserException() }
        return user.toDTO()
    }

    @Transactional
    fun updateUserPushToken(userId: Long, pushToken: String) {
        val user = userRepository.findById(userId).orElseThrow{ NotFoundUserException() }
        user.pushToken = pushToken
    }

    fun getUserNotifications(userId: Long, page: Int, size: Int): PageUserNotificationDTO {
        val pageable = PageRequest.of(page, size)

        val notifications = userNotificationRepository.findByUserId(userId, pageable)

        return PageUserNotificationDTO(
            notifications = notifications.content.map { it.toDTO() },
            totalPage = notifications.totalPages,
        )
    }

    @Transactional
    fun readUserNotification(userId: Long, userNotificationId: Long) {
        val user = userRepository.findById(userId).orElseThrow{ NotFoundUserException() }
        val notification = userNotificationRepository.findById(userNotificationId).orElseThrow { NotFoundUserException() }
        notification.isRead = true
    }
}