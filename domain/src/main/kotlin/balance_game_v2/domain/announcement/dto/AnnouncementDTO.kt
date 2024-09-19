package balance_game_v2.domain.announcement.dto

import balance_game_v2.domain.announcement.entity.Announcement
import balance_game_v2.domain.announcement.model.SearchCondition
import java.time.LocalDateTime

data class AnnouncementDTO(
    val announcementId: Long,
    val type: SearchCondition,
    val title: String,
    val content: String,
    val viewCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

fun Announcement.toDTO(): AnnouncementDTO {
    return AnnouncementDTO(
        announcementId = announcementId!!,
        type = type,
        title = title,
        content = content,
        viewCount = viewCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
