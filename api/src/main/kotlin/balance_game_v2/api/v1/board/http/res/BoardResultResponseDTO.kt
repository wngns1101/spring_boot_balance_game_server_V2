package balance_game_v2.api.v1.board.http.res

import domain.board.dto.BoardResultDTO

data class BoardResultResponseDTO (
    val results: List<BoardResultDTO>
)