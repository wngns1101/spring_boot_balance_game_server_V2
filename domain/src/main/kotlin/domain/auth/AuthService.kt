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
    fun signUp(email: String, password: String): Pair<String, AuthGroup> {
        if (authRepository.existsByEmail(email)) throw AlreadyExistEmailException()

        val auth = Auth(
            email = email,
            password = convertPassword(password),
            authGroup = AuthGroup.USER,
        )

        authRepository.save(auth)

        return Pair(email, auth.authGroup)
    }

    fun signIn(email: String, password: String): Pair<String, AuthGroup> {
        val auth = authRepository.findByEmailAndDeletedAtIsNull(email) ?: throw NotSignUpUserException()

        if (!verificationPassword(password, auth.password)) throw PasswordMismatchException()

        return Pair(auth.email, auth.authGroup)
    }

    fun convertPassword(password: String): String {
        return encoder.encode(password)
    }

    fun verificationPassword(password: String, authPassword: String): Boolean {
        return encoder.matches(password, authPassword)
    }

    @Transactional
    fun changeUserPassword(email: String, currentPassword: String, newPassword: String) {
        val auth = authRepository.findByEmailAndDeletedAtIsNull(email) ?: throw NotSignUpUserException()

        if (!encoder.matches(currentPassword, auth.password)) throw PasswordMismatchException()

        auth.password = encoder.encode(newPassword)
    }

    @Transactional
    fun updateToken(email: String, refreshToken: String) {
        val auth = authRepository.findByEmailAndDeletedAtIsNull(email) ?: throw NotSignUpUserException()
        auth.refreshToken = refreshToken
    }

    fun validationToken(email: String, token: String): Boolean {
        val auth = authRepository.findByEmailAndDeletedAtIsNull(email) ?: throw NotSignUpUserException()
        return auth.refreshToken.equals(token)
    }
}
