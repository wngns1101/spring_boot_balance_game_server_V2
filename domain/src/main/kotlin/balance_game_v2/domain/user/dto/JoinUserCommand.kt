package balance_game_v2.domain.user.dto

class JoinUserCommand(
    val accountName: String,
    val realName: String,
    val email: String,
    val pushToken: String?,
    val isCheckedMarketing: Boolean,
    val profileUrl: String?,
    val nickName: String?,
)
