package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardReviewReport
import java.time.LocalDateTime

data class BoardReviewReportDTO(
    val boardReviewReportId: Long,
    val boardReviewId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
)

fun BoardReviewReport.toDTO(): BoardReviewReportDTO {
    return BoardReviewReportDTO(
        boardReviewReportId = boardReviewReportId,
        boardReviewId = boardReviewId,
        userId = userId,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}
