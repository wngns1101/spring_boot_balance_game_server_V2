package balance_game_v2.api.v1.board.http.res

import domain.domain.board.dto.SimpleBoardDTO

class GetMyBoardsResponseDTO(
    val boards: List<SimpleBoardDTO>
)
