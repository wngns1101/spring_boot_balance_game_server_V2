package balance_game_v2.api.v1.common.http.res

import domain.domain.announcement.dto.AnnouncementDTO

data class AnnouncementDetailResponseDTO(
    val announcement: AnnouncementDTO,
)
