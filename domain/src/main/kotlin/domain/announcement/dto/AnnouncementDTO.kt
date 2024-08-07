package domain.domain.announcement.dto

import domain.announcement.entity.Announcement
import domain.announcement.model.SearchCondition
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
