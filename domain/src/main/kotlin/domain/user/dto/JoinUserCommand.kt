package domain.user.dto

class JoinUserCommand(
    val email: String,
    val realName: String,
    val birth: String,
    val phoneNumber: String,
    val pushToken: String?,
    val isCheckedMarketing: Boolean,
)
