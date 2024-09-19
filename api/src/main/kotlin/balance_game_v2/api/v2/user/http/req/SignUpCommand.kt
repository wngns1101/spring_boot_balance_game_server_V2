package balance_game_v2.api.v2.user.http.req

import balance_game_v2.domain.user.dto.JoinUserCommand

data class SignUpCommand(
    val accountName: String,
    val password: String,
    val joinUserCommand: JoinUserCommand,
)
