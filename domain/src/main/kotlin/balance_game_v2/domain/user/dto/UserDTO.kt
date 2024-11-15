package balance_game_v2.domain.user.dto

import balance_game_v2.domain.user.entity.User
import java.time.LocalDateTime

data class UserDTO(
    val userId: Long,
    val nickname: String,
    val realName: String,
    val accountName: String,
    val email: String,
    val pushToken: String? = null,
    val updatedAt: LocalDateTime,
    val profileUrl: String?,
)

fun User.toDTO(): UserDTO {
    return UserDTO(
        userId = userId!!,
        nickname = nickname,
        realName = realName,
        accountName = accountName,
        email = email,
        pushToken = pushToken,
        updatedAt = updatedAt,
        profileUrl = profileUrl,
    )
}
