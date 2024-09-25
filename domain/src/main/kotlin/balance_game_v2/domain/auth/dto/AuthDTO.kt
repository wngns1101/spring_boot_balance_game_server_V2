package balance_game_v2.domain.auth.dto

import balance_game_v2.domain.auth.model.AuthGroup
import balance_game_v2.domain.auth.model.AuthStatus

data class AuthDTO(
    val accountName: String,
    val authGroup: AuthGroup,
    val status: AuthStatus,
)
