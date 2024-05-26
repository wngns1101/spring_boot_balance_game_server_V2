package balance_game_v2.api.v1.user.http.res

import balance_game_v2.api.v1.user.application.TokenDTO

data class SignInResponseDTO(
    val token: TokenDTO
)