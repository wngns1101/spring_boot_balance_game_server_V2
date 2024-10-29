package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.Board
import java.time.LocalDateTime

data class BoardDetailDTO(
    val boardId: Long,
    val writer: WriterDTO,
    val title: String,
    val introduce: String,
    val likeCount: Int,
    val dislikeCount: Int,
    val viewCount: Int,
    val boardReviewCount: Int,
    val boardReviewsPreview: List<BoardReviewKeywordDTO>?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

data class WriterDTO(
    val userId: Long,
    val nickName: String,
)

fun Board.toBoardDetail(writer: WriterDTO, boardReviewCount: Int, boardReviewsPreview: List<BoardReviewKeywordDTO>): BoardDetailDTO {
    return BoardDetailDTO(
        boardId = boardId!!,
        writer = writer,
        title = title,
        introduce = introduce,
        likeCount = likeCount,
        dislikeCount = dislikeCount,
        viewCount = viewCount,
        boardReviewCount = boardReviewCount,
        boardReviewsPreview = boardReviewsPreview,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
