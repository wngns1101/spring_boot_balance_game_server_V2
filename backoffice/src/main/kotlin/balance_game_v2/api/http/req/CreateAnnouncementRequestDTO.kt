package balance_game_v2.api.http.req

import balance_game_v2.domain.announcement.dto.CreateAnnouncementCommandDTO

data class CreateAnnouncementRequestDTO(
    val title: String,
    val content: String,
    val type: String,
)

fun CreateAnnouncementRequestDTO.toCommand(): CreateAnnouncementCommandDTO {
    return CreateAnnouncementCommandDTO(
        title = title,
        content = content,
        type = type,
    )
}
