package balance_game_v2.api.v1.user.http.res

import domain.user.dto.UserReportDTO

data class ListUserReportResponseDTO(
    val reports: List<UserReportDTO>
)
