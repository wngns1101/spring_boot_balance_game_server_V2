package balance_game_v2.domain.user

import balance_game_v2.domain.auth.AuthService
import balance_game_v2.domain.auth.exception.NotFoundUserException
import balance_game_v2.domain.auth.model.AuthGroup
import balance_game_v2.domain.auth.repository.AuthRepository
import balance_game_v2.domain.board.exception.NotFoundException
import balance_game_v2.domain.board.repository.BoardContentItemRepository
import balance_game_v2.domain.board.repository.BoardContentRepository
import balance_game_v2.domain.board.repository.BoardKeywordRepository
import balance_game_v2.domain.board.repository.BoardReportRepository
import balance_game_v2.domain.board.repository.BoardRepository
import balance_game_v2.domain.board.repository.BoardResultRepository
import balance_game_v2.domain.board.repository.BoardReviewKeywordRepository
import balance_game_v2.domain.board.repository.BoardReviewReportRepository
import balance_game_v2.domain.board.repository.BoardReviewRepository
import balance_game_v2.domain.error.AlreadyExistEmailException
import balance_game_v2.domain.notification.repository.NotificationRepository
import balance_game_v2.domain.user.dto.JoinUserCommand
import balance_game_v2.domain.user.dto.PageUserNotificationDTO
import balance_game_v2.domain.user.dto.UserDTO
import balance_game_v2.domain.user.dto.WithDrawCommandDTO
import balance_game_v2.domain.user.dto.toDTO
import balance_game_v2.domain.user.entity.TermsAgreementHistory
import balance_game_v2.domain.user.entity.User
import balance_game_v2.domain.user.entity.UserNotification
import balance_game_v2.domain.user.model.TermsAgreementHistoryType
import balance_game_v2.domain.user.model.UserNotificationType
import balance_game_v2.domain.user.repository.AdjectiveNameRepository
import balance_game_v2.domain.user.repository.AnimalNameRepository
import balance_game_v2.domain.user.repository.BlockUserHistoryRepository
import balance_game_v2.domain.user.repository.TermsAgreementHistoryRepository
import balance_game_v2.domain.user.repository.UserNotificationRepository
import balance_game_v2.domain.user.repository.UserRepository
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
    private val adjectiveNameRepository: AdjectiveNameRepository,
    private val animalNameRepository: AnimalNameRepository,
    private val authRepository: AuthRepository,
    private val boardRepository: BoardRepository,
    private val blockUserHistoryRepository: BlockUserHistoryRepository,
    private val boardContentRepository: BoardContentRepository,
    private val boardContentItemRepository: BoardContentItemRepository,
    private val boardKeywordRepository: BoardKeywordRepository,
    private val boardReviewRepository: BoardReviewRepository,
    private val boardReviewKeywordRepository: BoardReviewKeywordRepository,
    private val boardReviewReportRepository: BoardReviewReportRepository,
    private val boardResultRepository: BoardResultRepository,
    private val boardReportRepository: BoardReportRepository,
) {
    @Transactional
    fun signUp(accountName: String, password: String, joinUserCommand: JoinUserCommand): Pair<String, AuthGroup> {
        val authResult = authService.signUp(accountName, password)

        if (userRepository.existsUserByEmail(joinUserCommand.email)) throw AlreadyExistEmailException()

        val user = User(
            accountName = joinUserCommand.accountName,
            nickname = joinUserCommand.nickName ?: nicknameMaker(),
            realName = joinUserCommand.realName,
            birth = joinUserCommand.birth,
            email = joinUserCommand.email,
            invitationCode = "",
            pushToken = joinUserCommand.pushToken,
            profileUrl = joinUserCommand.profileUrl,
        )

        userRepository.save(user)

        val termsAgreementHistories = mutableListOf<TermsAgreementHistory>()

        termsAgreementHistories += TermsAgreementHistory(
            userId = user.userId!!,
            type = TermsAgreementHistoryType.SERVICE,
            status = true
        )
        termsAgreementHistories += TermsAgreementHistory(
            userId = user.userId!!,
            type = TermsAgreementHistoryType.MARKETING,
            status = joinUserCommand.isCheckedMarketing
        )

        termsAgreementHistoryRepository.saveAll(termsAgreementHistories)

        val userNotifications = mutableListOf<UserNotification>()

        userNotifications += UserNotification(
            userId = user.userId!!,
            type = UserNotificationType.SERVICE,
            status = true
        )
        userNotifications += UserNotification(
            userId = user.userId!!,
            type = UserNotificationType.MARKETING,
            status = joinUserCommand.isCheckedMarketing
        )

        userNotificationRepository.saveAll(userNotifications)

        return authResult
    }

    fun getUserByAccountName(accountName: String): UserDTO {
        val user = userRepository.findByAccountNameAndDeletedAtIsNull(accountName)
        user.let { return user!!.toDTO() }
    }

    @Transactional
    fun modifyUserInfo(userId: Long, nickName: String, profileUrl: String?) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        user.nickname = nickName
        user.profileUrl = profileUrl
    }

    @Transactional
    fun withdraw(userId: Long, command: WithDrawCommandDTO) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }
        user.deletedAt = LocalDateTime.now()
        user.deletedReason = command.reason
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

        val marketingAgreement =
            termsAgreementHistoryRepository.findByUserIdAndType(user.userId!!, TermsAgreementHistoryType.MARKETING)
                ?: throw NotFoundException()
        termsAgreementHistoryRepository.delete(marketingAgreement)

        val newMarketingAgreement =
            TermsAgreementHistory(
                userId = userId,
                type = TermsAgreementHistoryType.MARKETING,
                status = !marketingAgreement.status
            )
        termsAgreementHistoryRepository.save(newMarketingAgreement)

        return newMarketingAgreement.status
    }

    fun getUserNotifications(userId: Long): List<balance_game_v2.domain.user.dto.UserNotificationDTO> {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        return userNotificationRepository.findAllByUserId(user.userId!!).map { it.toDTO() }
    }

    @Transactional
    fun modifyUserNotifications(userId: Long, userNotificationId: Long): Boolean {
        val user = userRepository.findById(userId).orElseThrow { NotFoundUserException() }

        val userNotification =
            userNotificationRepository.findById(userNotificationId).orElseThrow { NotFoundUserException() }
        userNotification.status = !userNotification.status

        return userNotification.status
    }

    private fun nicknameMaker(): String {
        val animalNames = animalNameRepository.findAll().map { it.name }
        val adjectiveNames = adjectiveNameRepository.findAll().map { it.name }
        val nickname: String

        while (true) {
            val randomAnimalName = animalNames.random()
            val randomAdjectiveName = adjectiveNames.random()
            val randomNum = (1..100).random()

            val combineNickname = "$randomAdjectiveName$randomAnimalName$randomNum"

            if (!userRepository.existsUserByNickname(combineNickname)) {
                nickname = combineNickname
                break
            }
        }

        return nickname
    }

    @Transactional
    fun withdraw(accountNames: List<String>) {
        authRepository.findAllByAccountNameIn(accountNames).let { authRepository.deleteAllInBatch(it) }
        val users = userRepository.findAllByAccountNameIn(accountNames).mapNotNull { it.userId }
        users.forEach {
            blockUserHistoryRepository.deleteByUserId(it)

            val boards = boardRepository.findAllByUserId(it)
            val boardIds = boards.map { it.boardId!! }

            boardKeywordRepository.findAllByBoardIdIn(boardIds)
                .let { boardKeywordRepository.deleteAllInBatch(it) }

            val boardContents = boardContentRepository.findAllByBoardIdIn(boardIds)
            val boardContentIds = boardContents.map { it.boardContentId!! }
            boardContentItemRepository.findAllByBoardContentIdIn(boardContentIds)
                .let { boardContentItemRepository.deleteAllInBatch(it) }

            boardResultRepository.deleteByUserId(it)
            boardReportRepository.deleteByUserId(it)
            boardReviewRepository.deleteByUserId(it)
            boardReviewKeywordRepository.deleteByUserId(it)
            boardReviewReportRepository.deleteByUserId(it)

            termsAgreementHistoryRepository.deleteByUserId(it)
            userNotificationRepository.deleteByUserId(it)

            userRepository.deleteByUserId(it)
            boardRepository.deleteAllInBatch(boards)
            boardContentRepository.deleteAllInBatch(boardContents)
        }
    }

    fun getUserByEmail(email: String): UserDTO {
        val user = userRepository.findByEmailAndDeletedAtIsNull(email) ?: throw NotFoundUserException()
        return user.toDTO()
    }
}
