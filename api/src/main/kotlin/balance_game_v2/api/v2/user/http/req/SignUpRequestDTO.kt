package balance_game_v2.api.v2.user.http.req

import balance_game_v2.domain.user.dto.JoinUserCommand

data class SignUpRequestDTO(
    val accountName: String,
    val password: String,
    val realName: String,
    val email: String,
    val pushToken: String?,
    val isCheckedMarketing: Boolean,
    val profileUrl: String?,
    val nickName: String?,
) {
    fun toCommand(request: SignUpRequestDTO): SignUpCommand {
        return SignUpCommand(
            accountName = request.accountName,
            password = request.password,
            joinUserCommand = JoinUserCommand(
                accountName = request.accountName,
                realName = request.realName,
                email = request.email,
                pushToken = request.pushToken,
                isCheckedMarketing = request.isCheckedMarketing,
                profileUrl = request.profileUrl,
                nickName = request.nickName,
            )
        )
    }
}
