package balance_game_v2.api.v2.user.http.res

import balance_game_v2.api.v2.user.application.TokenDTO

data class SignUpResponseDTO(
    val token: TokenDTO
)
