package balance_game_v2.api.v1.user.http.res

data class MainUserInfoResponseDTO(
    val userId: Long,
    val nickName: String,
    val myBoardCount: Int,
)
