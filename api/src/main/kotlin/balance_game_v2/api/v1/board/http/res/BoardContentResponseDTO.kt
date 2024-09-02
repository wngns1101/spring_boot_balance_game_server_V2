package balance_game_v2.api.v1.board.http.res

import domain.domain.board.dto.BoardContentList

data class BoardContentResponseDTO(
    val boardContents: BoardContentList
)
