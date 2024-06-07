package domain.user.dto

import domain.user.entity.UserReport
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
