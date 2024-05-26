package balance_game_v2.api.v1.board.http.res

import domain.board.dto.PageBoardDTO

data class PageBoardResponseDTO (
    val boards: PageBoardDTO
)