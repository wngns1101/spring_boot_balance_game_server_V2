package domain.user

import domain.auth.AuthService
import domain.auth.exception.NotFoundUserException
import domain.user.dto.JoinUserCommand
import domain.auth.model.AuthGroup
import domain.error.AlreadySignUpException
import domain.user.dto.UserDTO
import domain.user.dto.toDTO
import domain.user.entity.User
import domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService (
    private val userRepository: UserRepository,
    private val authService: AuthService,
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
        )

        userRepository.save(user)

        return authResult
    }

    fun getUserByEmail(email: String): UserDTO {
        println(email)
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
}