package domain.board.dto

import domain.board.entity.BoardReviewReport
import java.time.LocalDateTime

data class BoardReviewReportDTO(
    val boardReviewReportId: Long,
    val boardReviewId: Long,
    val userId: Long,
    val createdAt: LocalDateTime,
)

fun BoardReviewReport.toDTO(): BoardReviewReportDTO {
    return BoardReviewReportDTO(
        boardReviewReportId = boardReviewReportId,
        boardReviewId = boardReviewReportId,
        userId = userId,
        createdAt = createdAt,
    )
}
