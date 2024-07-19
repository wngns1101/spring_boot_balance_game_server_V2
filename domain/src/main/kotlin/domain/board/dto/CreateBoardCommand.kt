package domain.board.dto

data class CreateBoardCommand(
    val themeId: Long,
    val title: String,
    val introduce: String,
    val keywords: List<String>,
    val boardContents: List<BoardContentCommand>
)

data class BoardContentCommand(
    val title: String,
    val items: List<String>,
)
