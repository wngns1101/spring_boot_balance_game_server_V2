package balance_game_v2.domain.user.dto

data class UserPageDTO(
    val users: List<UserDTO>,
    val totalPage: Int
)
