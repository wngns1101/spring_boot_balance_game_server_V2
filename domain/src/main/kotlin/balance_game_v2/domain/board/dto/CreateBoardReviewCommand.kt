package balance_game_v2.domain.board.dto

data class CreateBoardReviewCommand(
    val title: String,
    val comment: String,
    val keywords: List<String>,
    val isLike: Boolean,
    val isDislike: Boolean,
)
