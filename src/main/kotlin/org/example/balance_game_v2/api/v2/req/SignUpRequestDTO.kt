package org.example.balance_game_v2.api.v2.req

data class SignUpRequestDTO (
    val email: String,
    val password: String,
    val nickname: String,
    val userName: String,
    val birth: String
){
    fun toCommand(request: SignUpRequestDTO): SignUpCommand {
        return SignUpCommand(
            email = request.email,
            password = request.password,
            joinUserCommand = JoinUserCommand(
                email = request.email,
                realName = request.userName,
                nickname = request.nickname,
                birth = request.birth
            )
        )
    }
}