package balance_game_v2.api.v1.board.http.req

import domain.board.dto.BoardContentCommand
import domain.board.dto.BoardContentDTO
import domain.board.dto.ModifyBoardCommand

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
