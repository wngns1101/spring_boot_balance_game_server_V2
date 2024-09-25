package balance_game_v2.api.v2.user.application

import balance_game_v2.domain.auth.model.AuthStatus

data class SignInDTO(
    val accessToken: String,
    val refreshToken: String,
    val status: AuthStatus
)
