package domain.user.dto

import domain.user.entity.User
import java.time.LocalDateTime

data class UserDTO(
    val userId: Long,
    val nickname: String,
    val realName: String,
    val accountName: String,
    val birth: String,
    val email: String,
    val invitationCode: String,
    val pushToken: String? = null,
    val updatedAt: LocalDateTime
)

fun User.toDTO(): UserDTO {
    return UserDTO(
        userId = userId!!,
        nickname = nickname,
        realName = realName,
        accountName = accountName,
        birth = birth,
        email = email,
        invitationCode = invitationCode,
        pushToken = pushToken,
        updatedAt = updatedAt
    )
}
