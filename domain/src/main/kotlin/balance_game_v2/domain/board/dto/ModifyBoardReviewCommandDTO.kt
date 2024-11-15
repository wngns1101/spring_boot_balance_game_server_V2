package balance_game_v2.domain.board.dto

data class ModifyBoardReviewCommandDTO(
    val boardReviewId: Long,
    val boardId: Long,
    val userId: Long,
    val title: String,
    val comment: String,
    val isLike: Boolean,
    val isDislike: Boolean,
    val keywords: String?
)
