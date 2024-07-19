package balance_game_v2.api.v1.board.http.req

import domain.board.dto.BoardContentCommand
import domain.board.dto.CreateBoardCommand

data class CreateBoardRequestDTO(
    val themeId: Long,
    val title: String,
    val introduce: String,
    val keywords: List<String>,
    val boardContents: List<BoardContentRequestDTO>
)

data class BoardContentRequestDTO(
    val title: String,
    val items: List<String>,
)

fun CreateBoardRequestDTO.toCommand(): CreateBoardCommand {
    return CreateBoardCommand(
        themeId = themeId,
        title = title,
        introduce = introduce,
        keywords = keywords,
        boardContents = boardContents.map {
            BoardContentCommand(
                title = it.title,
                items = it.items
            )
        }
    )
}
