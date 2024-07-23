package domain.user.dto

class JoinUserCommand(
    val accountName: String,
    val realName: String,
    val birth: String,
    val email: String,
    val pushToken: String?,
    val isCheckedMarketing: Boolean,
    val profileUrl: String?
)
