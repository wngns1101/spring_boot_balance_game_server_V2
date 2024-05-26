package balance_game_v2.api.v1.user.http.res

import domain.user.dto.UserDTO

data class UserDetailResponseDTO (
    val user: UserDTO
)