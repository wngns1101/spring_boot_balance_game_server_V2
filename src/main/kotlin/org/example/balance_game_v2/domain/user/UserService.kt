package org.example.balance_game_v2.domain.user;

import org.example.balance_game_v2.api.v2.req.JoinUserCommand
import org.example.balance_game_v2.api.v2.req.SignUpCommand
import org.example.balance_game_v2.domain.error.AlreadyExistEmailException
import org.example.balance_game_v2.domain.error.AlreadySignUpException
import org.example.balance_game_v2.domain.user.entity.User
import org.example.balance_game_v2.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
class UserService (
    private val userRepository: UserRepository,
    private val authService: AuthService,
){
    fun signUp(email: String, password: String, joinUserCommand: JoinUserCommand):String {
        val authResult = authService.signUp(email, password)
        val userResult = userRepository.findByEmailAndDeletedAtIsNull(authResult)

        if (userResult != null) throw AlreadySignUpException()

        val user = User(
            email = authResult,
            nickname = joinUserCommand.nickname,
            realName = joinUserCommand.realName,
            birth = joinUserCommand.birth,
        )

        userRepository.save(user)

        return authResult
    }
}
