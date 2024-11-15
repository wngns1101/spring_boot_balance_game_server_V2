package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardReview

data class BoardReviewDTO(
    val boardReviewId: Long,
    val boardId: Long,
    val userId: Long,
    val nickname: String,
    val title: String,
    val comment: String,
    val keywords: List<String>,
    val isLike: Boolean,
    val isDislike: Boolean,
    val profile: String?,
)

fun BoardReview.toDTO(keywords: List<String>, nickname: String, profile: String?): BoardReviewDTO {
    return BoardReviewDTO(
        boardReviewId = boardReviewId!!,
        boardId = boardId,
        userId = userId,
        nickname = nickname,
        title = title,
        comment = comment,
        keywords = keywords,
        isLike = isLike,
        isDislike = isDislike,
        profile = profile,
    )
}
