package balance_game_v2.api.v1.user.http.req

import domain.user.dto.JoinUserCommand

data class SignUpCommand(
    val accountName: String,
    val password: String,
    val joinUserCommand: JoinUserCommand,
)
