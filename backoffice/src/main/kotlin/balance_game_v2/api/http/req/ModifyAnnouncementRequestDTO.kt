package balance_game_v2.api.http.req

import balance_game_v2.domain.announcement.dto.ModifyAnnouncementCommandDTO

data class ModifyAnnouncementRequestDTO(
    val announcementId: Long,
    val title: String,
    val content: String,
    val type: String,
)

fun ModifyAnnouncementRequestDTO.toCommand(): ModifyAnnouncementCommandDTO {
    return ModifyAnnouncementCommandDTO(
        announcementId = announcementId,
        title = title,
        content = content,
        type = type
    )
}
