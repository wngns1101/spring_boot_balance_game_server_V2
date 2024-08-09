package domain.board.dto

import domain.board.entity.BoardReview

data class BoardReviewDTO(
    val boardReviewId: Long? = null,
    val boardId: Long,
    val userId: Long,
    val comment: String,
)

fun BoardReview.toDTO(): BoardReviewDTO {
    return BoardReviewDTO(
        boardReviewId = boardReviewId,
        boardId = boardId,
        userId = userId,
        comment = comment,
    )
}
