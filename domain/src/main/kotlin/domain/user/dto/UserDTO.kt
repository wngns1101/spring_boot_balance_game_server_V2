package domain.user.dto

import domain.user.entity.User
import java.time.LocalDateTime

data class UserDTO(
    val userId: Long,
    val nickname: String,
    val realName: String,
    val email: String,
    val birth: String,
    val phoneNumber: String,
    val invitationCode: String,
    val pushToken: String? = null,
    val updatedAt: LocalDateTime
)

fun User.toDTO(): UserDTO {
    return UserDTO(
        userId = userId!!,
        nickname = nickname,
        realName = realName,
        email = email,
        birth = birth,
        phoneNumber = phoneNumber,
        invitationCode = invitationCode,
        pushToken = pushToken,
        updatedAt = updatedAt
    )
}
