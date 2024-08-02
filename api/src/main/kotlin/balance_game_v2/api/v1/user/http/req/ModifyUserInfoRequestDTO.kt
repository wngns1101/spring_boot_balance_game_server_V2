package balance_game_v2.api.v1.user.http.req

data class ModifyUserInfoRequestDTO(
    val nickName: String,
    val profileUrl: String?,
)
