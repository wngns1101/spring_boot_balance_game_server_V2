package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardReview
import java.time.LocalDateTime

data class PageBoardReviewDTO(
    val boardReviews: List<BoardReviewListDTO>,
    val totalPage: Int,
)

data class BoardReviewListDTO(
    val boardReviewId: Long? = null,
    val boardId: Long,
    val userId: Long,
    var title: String,
    var comment: String,
    var isLike: Boolean,
    var isDislike: Boolean,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var deletedAt: LocalDateTime?
)

fun BoardReview.toPageBoardReviewDTO(): BoardReviewListDTO {
    return BoardReviewListDTO(
        boardReviewId = boardReviewId!!,
        boardId = boardId,
        userId = userId,
        title = title,
        comment = comment,
        isLike = isLike,
        isDislike = isDislike,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}
