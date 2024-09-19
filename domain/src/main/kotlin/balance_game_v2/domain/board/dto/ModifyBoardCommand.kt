package balance_game_v2.domain.board.dto

data class ModifyBoardCommand(
    val title: String,
    val content: String,
    val boardContent: List<BoardContentCommand>
)
