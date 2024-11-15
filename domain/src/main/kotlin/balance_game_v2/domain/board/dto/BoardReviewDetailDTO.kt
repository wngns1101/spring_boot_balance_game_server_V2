package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardReview
import java.time.LocalDateTime

data class BoardReviewDetailDTO(
    val boardReviewId: Long? = null,
    val boardId: Long,
    val userId: Long,
    var title: String,
    var comment: String,
    var isLike: Boolean,
    var isDislike: Boolean,
    val keywords: String?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var deletedAt: LocalDateTime?
)

fun BoardReview.toDetailDTO(keywords: String): BoardReviewDetailDTO {
    return BoardReviewDetailDTO(
        boardReviewId = boardReviewId,
        boardId = boardId,
        userId = userId,
        title = title,
        comment = comment,
        isLike = isLike,
        isDislike = isDislike,
        keywords = keywords,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}
