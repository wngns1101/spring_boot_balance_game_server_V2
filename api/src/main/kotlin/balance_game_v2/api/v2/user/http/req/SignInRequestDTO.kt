package balance_game_v2.api.v2.user.http.req

data class SignInRequestDTO(
    val accountName: String,
    val password: String,
    val pushToken: String?,
)
