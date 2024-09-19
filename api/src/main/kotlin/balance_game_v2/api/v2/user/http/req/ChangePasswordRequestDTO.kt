package balance_game_v2.api.v2.user.http.req

data class ChangePasswordRequestDTO(
    val currentPassword: String,
    val newPassword: String,
)
