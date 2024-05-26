package balance_game_v2.api.v1.board.http.res

import domain.board.dto.BoardDetailDTO

data class BoardDetailResponseDTO (
    val board: BoardDetailDTO,
)