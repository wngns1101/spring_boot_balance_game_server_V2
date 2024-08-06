package balance_game_v2.api.v1.board.http.res

import domain.domain.board.dto.SimpleBoardDTO

data class TodayRecommendGameResponseDTO(
    val board: SimpleBoardDTO
)
