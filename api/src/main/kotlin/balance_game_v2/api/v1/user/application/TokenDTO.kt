package balance_game_v2.api.v1.user.application

data class TokenDTO (
    val accessToken: String,
    val refreshToken: String,
)