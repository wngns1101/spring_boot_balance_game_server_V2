package balance_game_v2.domain.board.dto

data class CreateBoardResultRequestCommand(
    val boardContentId: Long,
    val boardContentItemId: Long,
)
