package balance_game_v2.api.v1.board.http.res

import domain.board.dto.BoardListDTO

class GetMyBoardsResponseDTO(
    val boards: List<BoardListDTO>
)
