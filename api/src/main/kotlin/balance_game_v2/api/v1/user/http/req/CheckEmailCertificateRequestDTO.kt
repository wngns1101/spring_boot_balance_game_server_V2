package balance_game_v2.api.v1.user.http.req

data class CheckEmailCertificateRequestDTO(
    val email: String,
    val code: String,
)
