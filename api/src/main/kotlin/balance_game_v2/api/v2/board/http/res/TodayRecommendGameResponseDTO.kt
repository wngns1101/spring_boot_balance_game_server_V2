package balance_game_v2.api.v2.board.http.res

import balance_game_v2.domain.board.dto.SimpleBoardDTO

data class TodayRecommendGameResponseDTO(
    val board: SimpleBoardDTO
)
