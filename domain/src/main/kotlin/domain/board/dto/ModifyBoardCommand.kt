package domain.board.dto

data class ModifyBoardCommand(
    val title: String,
    val content: String,
    val boardContent: List<BoardContentCommand>
)
