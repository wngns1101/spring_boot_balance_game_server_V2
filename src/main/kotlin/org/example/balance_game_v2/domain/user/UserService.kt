package org.example.balance_game_v2.domain.user;

import org.example.balance_game_v2.api.v2.req.SignUpCommand
import org.example.balance_game_v2.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
class UserService (
    private val userRepository: UserRepository
){
    fun signUp(userCommand: SignUpCommand) {
        authService.signUp(userCommand.email, userCommand.password)
    }
}
