package balance_game_v2.api.v1.board.http.req

import domain.board.dto.BoardContentCommand
import domain.board.dto.CreateBoardCommand

data class CreateBoardRequestDTO(
    val title: String,
    val content: String,
    val boardContent: List<BoardContentRequestDTO>
)

data class BoardContentRequestDTO(
    val title: String,
    val photoUrl: String,
)

fun CreateBoardRequestDTO.toCommand(): CreateBoardCommand {
    return CreateBoardCommand(
        title = title,
        content = content,
        boardContent = boardContent.map {
            BoardContentCommand(
                title = it.title,
                photoUrl = it.photoUrl,
            )
        }
    )
}