package balance_game_v2.domain.board.dto

data class ModifyBoardReviewCommand(
    val boardReviewId: Long,
    val comment: String,
    val keywords: List<String>,
)
