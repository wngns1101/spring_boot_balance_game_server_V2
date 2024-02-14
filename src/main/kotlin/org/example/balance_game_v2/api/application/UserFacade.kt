package org.example.balance_game_v2.api.application

import org.example.balance_game_v2.api.v2.req.SignUpCommand
import org.example.balance_game_v2.domain.user.UserService
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userService: UserService
){
    fun signUp(userCommand: SignUpCommand) {
        val user = userService.signUp(userCommand.email, userCommand.password, userCommand.joinUserCommand)
    }
}
