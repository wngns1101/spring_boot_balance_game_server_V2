package balance_game_v2.api.v1.user.http.req

import domain.user.dto.JoinUserCommand

data class SignUpRequestDTO(
    val email: String,
    val password: String,
    val userName: String,
    val birth: String,
    val phoneNumber: String,
    val pushToken: String?,
    val isCheckedMarketing: Boolean,
) {
    fun toCommand(request: SignUpRequestDTO): SignUpCommand {
        return SignUpCommand(
            email = request.email,
            password = request.password,
            joinUserCommand = JoinUserCommand(
                email = request.email,
                realName = request.userName,
                birth = request.birth,
                phoneNumber = request.phoneNumber,
                pushToken = request.pushToken,
                isCheckedMarketing = request.isCheckedMarketing
            )
        )
    }
}
