package balance_game_v2.domain.user.dto

import balance_game_v2.domain.user.entity.UserReport
import java.time.LocalDateTime

data class UserReportDTO(
    val userId: Long,
    val targetUserId: Long,
    val updatedAt: LocalDateTime
)

fun UserReport.toDTO(): UserReportDTO {
    return UserReportDTO(
        userId = userId,
        targetUserId = targetUserId,
        updatedAt = updatedAt,
    )
}
