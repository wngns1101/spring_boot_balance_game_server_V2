package balance_game_v2.api.v1.user.http.req

import domain.user.dto.JoinUserCommand

data class SignUpRequestDTO(
    val accountName: String,
    val password: String,
    val realName: String,
    val birth: String,
    val email: String,
    val pushToken: String?,
    val isCheckedMarketing: Boolean,
    val profileUrl: String?,
) {
    fun toCommand(request: SignUpRequestDTO): SignUpCommand {
        return SignUpCommand(
            accountName = request.accountName,
            password = request.password,
            joinUserCommand = JoinUserCommand(
                accountName = request.accountName,
                realName = request.realName,
                birth = request.birth,
                email = request.email,
                pushToken = request.pushToken,
                isCheckedMarketing = request.isCheckedMarketing,
                profileUrl = request.profileUrl,
            )
        )
    }
}
