package balance_game_v2.api.v2.board.http.res

import balance_game_v2.domain.board.dto.BoardListDTO

class GetMyBoardsResponseDTO(
    val boards: List<BoardListDTO>
)
