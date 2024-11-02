package balance_game_v2.api.http.req

import balance_game_v2.domain.user.dto.ModifyUserCommandDTO

data class ModifyUserRequestDTO(
    val userId: Long,
    val realName: String,
    val profileUrl: String,
    val nickname: String,
    val email: String,
    val birth: String,
    val accountName: String,
    val authId: Long,
    val authStatus: String,
)

fun ModifyUserRequestDTO.toCommand(): ModifyUserCommandDTO {
    return ModifyUserCommandDTO(
        userId = userId,
        realName = realName,
        profileUrl = profileUrl,
        nickname = nickname,
        email = email,
        birth = birth,
        accountName = accountName,
    )
}
