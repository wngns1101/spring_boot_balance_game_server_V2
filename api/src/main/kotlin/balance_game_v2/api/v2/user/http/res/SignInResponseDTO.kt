package balance_game_v2.api.v2.user.http.res

import balance_game_v2.api.v2.user.application.SignInDTO

data class SignInResponseDTO(
    val token: SignInDTO
)
