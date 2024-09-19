package balance_game_v2.api.v2.board.http.res

import balance_game_v2.domain.board.dto.BoardReviewDTO

data class GetWroteReviewsResponseDTO(
    val reviews: List<BoardReviewDTO>
)
