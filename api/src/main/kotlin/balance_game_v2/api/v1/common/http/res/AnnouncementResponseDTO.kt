package balance_game_v2.api.v1.common.http.res

import domain.announcement.dto.AnnouncementSimpleDTO

data class AnnouncementResponseDTO(
    val announcements: List<AnnouncementSimpleDTO>
)
