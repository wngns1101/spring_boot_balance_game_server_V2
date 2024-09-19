package balance_game_v2.api.v2.board.http.req

import balance_game_v2.domain.board.dto.BoardContentCommand
import balance_game_v2.domain.board.dto.BoardContentDTO
import balance_game_v2.domain.board.dto.ModifyBoardCommand

data class BoardModifyRequestDTO(
    val title: String,
    val content: String,
    val boardContent: List<BoardContentDTO>,
)

fun BoardModifyRequestDTO.toCommand(): ModifyBoardCommand {
    return ModifyBoardCommand(
        title = title,
        content = content,
        boardContent = boardContent.map { it.toCommand() },
    )
}

fun BoardContentDTO.toCommand(): BoardContentCommand {
    return BoardContentCommand(
        title = title,
        items = emptyList(),
    )
}
