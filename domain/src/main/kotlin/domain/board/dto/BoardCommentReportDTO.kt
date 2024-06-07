package domain.board.dto

import domain.board.entity.BoardCommentReport
import java.time.LocalDateTime

data class BoardCommentReportDTO(
    val boardCommentReportId: Long,
    val boardCommentId: Long,
    val userId: Long,
    val createdAt: LocalDateTime,
)

fun BoardCommentReport.toDTO(): BoardCommentReportDTO {
    return BoardCommentReportDTO(
        boardCommentReportId = boardCommentReportId,
        boardCommentId = boardCommentId,
        userId = userId,
        createdAt = createdAt,
    )
}
