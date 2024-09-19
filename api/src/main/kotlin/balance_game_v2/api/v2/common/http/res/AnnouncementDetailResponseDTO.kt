package balance_game_v2.api.v2.common.http.res

import balance_game_v2.domain.announcement.dto.AnnouncementDTO

data class AnnouncementDetailResponseDTO(
    val announcement: AnnouncementDTO,
)
