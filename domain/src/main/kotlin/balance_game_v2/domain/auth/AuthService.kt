package balance_game_v2.domain.auth

import balance_game_v2.domain.admin.repository.AdminRepository
import balance_game_v2.domain.auth.entity.Auth
import balance_game_v2.domain.auth.exception.BlockUserException
import balance_game_v2.domain.auth.exception.NotFoundUserException
import balance_game_v2.domain.auth.exception.NotSignUpUserException
import balance_game_v2.domain.auth.exception.PasswordMismatchException
import balance_game_v2.domain.auth.exception.WithDrawUserException
import balance_game_v2.domain.auth.model.AuthGroup
import balance_game_v2.domain.auth.model.AuthStatus
import balance_game_v2.domain.auth.repository.AuthRepository
import balance_game_v2.domain.board.exception.NotFoundException
import balance_game_v2.domain.error.AlreadyExistEmailException
import balance_game_v2.domain.user.repository.BlockUserHistoryRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService(
    private val authRepository: AuthRepository,
    private val blockUserHistoryRepository: BlockUserHistoryRepository,
    private val adminRepository: AdminRepository,
) {
    final val encoder = BCryptPasswordEncoder(10)

    @Transactional
    fun signUp(accountName: String, password: String): Pair<String, AuthGroup> {
        if (authRepository.existsByAccountName(accountName)) throw AlreadyExistEmailException()

        val auth = Auth(
            accountName = accountName,
            password = convertPassword(password),
            authGroup = AuthGroup.USER,
            status = AuthStatus.ACTIVITY,
        )

        authRepository.save(auth)

        return Pair(accountName, auth.authGroup)
    }

    fun signIn(accountName: String, password: String): Pair<String, AuthGroup> {
        val auth = authRepository.findByAccountNameAndDeletedAtIsNull(accountName) ?: throw NotSignUpUserException()

        if (!verificationPassword(password, auth.password)) throw PasswordMismatchException()

        return Pair(auth.accountName, auth.authGroup)
    }

    fun convertPassword(password: String): String {
        return encoder.encode(password)
    }

    fun verificationPassword(password: String, authPassword: String): Boolean {
        return encoder.matches(password, authPassword)
    }

    @Transactional
    fun changeUserPassword(accountName: String, currentPassword: String, newPassword: String) {
        val auth = authRepository.findByAccountNameAndDeletedAtIsNull(accountName) ?: throw NotSignUpUserException()
        println(auth)
        if (!encoder.matches(currentPassword, auth.password)) throw PasswordMismatchException()

        auth.password = encoder.encode(newPassword)
    }

    @Transactional
    fun updateToken(accountName: String, refreshToken: String) {
        val auth = authRepository.findByAccountNameAndDeletedAtIsNull(accountName) ?: throw NotSignUpUserException()
        auth.refreshToken = refreshToken
    }

    fun validationToken(accountName: String, token: String): Boolean {
        val auth = authRepository.findByAccountNameAndDeletedAtIsNull(accountName) ?: throw NotSignUpUserException()
        return auth.refreshToken.equals(token)
    }

    fun checkDuplicateEmail(accountName: String): Boolean {
        return authRepository.existsByAccountName(accountName)
    }

    fun checkTokenValidation(accountName: String) {
        val auth = authRepository.findByAccountName(accountName) ?: throw NotSignUpUserException()
        if (auth.deletedAt != null) throw WithDrawUserException()
        if (auth.status == AuthStatus.BLOCK) throw BlockUserException()
    }

    fun checkBlockReason(userId: Long): BlockReasonDTO {
        val blockHistory = blockUserHistoryRepository.findByUserId(userId) ?: throw NotFoundUserException()
        val admin = adminRepository.findById(blockHistory.adminId).orElseThrow { NotFoundException() }

        return blockHistory.toDTO(admin.realName)
    }

    @Transactional
    fun withdraw(accountName: String) {
        val auth = authRepository.findByAccountNameAndDeletedAtIsNull(accountName) ?: throw NotSignUpUserException()
        auth.deletedAt = LocalDateTime.now()
        auth.status = AuthStatus.DELETE
    }

    @Transactional
    fun updatePassword(email: String, encodePw: String) {
        val auth = authRepository.findByAccountNameAndDeletedAtIsNull(email) ?: throw NotFoundUserException()
        auth.password = encodePw
    }
}
