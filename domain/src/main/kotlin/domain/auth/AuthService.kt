package domain.auth

import domain.auth.entity.Auth
import domain.auth.exception.NotSignUpUserException
import domain.auth.exception.PasswordMismatchException
import domain.auth.model.AuthGroup
import domain.auth.repository.AuthRepository
import domain.error.AlreadyExistEmailException
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authRepository: AuthRepository
) {
    final val encoder = BCryptPasswordEncoder(10)

    @Transactional
    fun signUp(accountName: String, password: String): Pair<String, AuthGroup> {
        if (authRepository.existsByAccountName(accountName)) throw AlreadyExistEmailException()

        val auth = Auth(
            accountName = accountName,
            password = convertPassword(password),
            authGroup = AuthGroup.USER,
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
}
