package org.example.balance_game_v2.api.v2.req

data class SignUpCommand (
    val email: String,
    val password: String,
    val joinUserCommand: JoinUserCommand,
)
