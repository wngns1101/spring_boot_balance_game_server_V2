package domain.board.dto

data class CreateBoardCommand(
    val title: String,
    val content: String,
    val boardContent: List<BoardContentCommand>
)

data class BoardContentCommand(
    val title: String,
    val photoUrl: String,
)
