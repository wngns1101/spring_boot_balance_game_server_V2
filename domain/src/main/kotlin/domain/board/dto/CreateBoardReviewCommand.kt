package domain.board.dto

data class CreateBoardReviewCommand(
    val comment: String,
    val keywords: List<String>,
    val isLike: Boolean,
    val isDislike: Boolean,
)
