package domain.announcement.dto

import domain.announcement.entity.Announcement
import domain.announcement.model.SearchCondition
import java.time.LocalDateTime

data class AnnouncementSimpleDTO(
    val announcementId: Long,
    val type: SearchCondition,
    val title: String,
    val createdAt: LocalDateTime,
)

fun Announcement.toSimpleDTO(): AnnouncementSimpleDTO {
    return AnnouncementSimpleDTO(
        announcementId = announcementId!!,
        type = type,
        title = title,
        createdAt = createdAt,
    )
}
