package balance_game_v2.api.v1.board.http.res

import domain.board.dto.BoardReviewDTO

data class GetWroteReviewsResponseDTO(
    val reviews: List<BoardReviewDTO>
)