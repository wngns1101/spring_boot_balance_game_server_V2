package balance_game_v2.api.v2.common.http.res

import balance_game_v2.domain.announcement.dto.AnnouncementSimpleDTO

data class AnnouncementResponseDTO(
    val announcements: List<AnnouncementSimpleDTO>
)
