package balance_game_v2.domain.board.dto

import balance_game_v2.domain.user.dto.UserDTO

data class BoardDetailByAdminDTO(
    val board: BoardListDTO,
    val user: UserDTO,
    val boardContents: List<BoardContentDTO>,
)
