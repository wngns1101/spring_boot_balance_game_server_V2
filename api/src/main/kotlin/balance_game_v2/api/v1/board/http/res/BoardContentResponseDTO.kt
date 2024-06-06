package balance_game_v2.api.v1.board.http.res

import domain.board.dto.BoardContentDTO

data class BoardContentResponseDTO(
    val boardContents: List<BoardContentDTO>
)
