package balance_game_v2.api.v2.user.http.res

import balance_game_v2.domain.user.dto.UserReportDTO

data class ListUserReportResponseDTO(
    val reports: List<UserReportDTO>
)
