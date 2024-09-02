package domain.board.dto

import domain.board.entity.BoardReview

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
)

fun BoardReview.toDTO(keywords: List<String>, nickname: String): BoardReviewDTO {
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
    )
}
