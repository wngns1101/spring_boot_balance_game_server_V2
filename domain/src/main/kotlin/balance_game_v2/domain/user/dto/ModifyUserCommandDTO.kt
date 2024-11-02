package balance_game_v2.domain.user.dto

data class ModifyUserCommandDTO(
    val userId: Long,
    val realName: String,
    val profileUrl: String,
    val nickname: String,
    val email: String,
    val birth: String,
    val accountName: String,
)
