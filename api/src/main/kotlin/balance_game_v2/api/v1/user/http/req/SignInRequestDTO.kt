package balance_game_v2.api.v1.user.http.req

data class SignInRequestDTO (
    val email: String,
    val password: String,
    val pushToken: String?,
)