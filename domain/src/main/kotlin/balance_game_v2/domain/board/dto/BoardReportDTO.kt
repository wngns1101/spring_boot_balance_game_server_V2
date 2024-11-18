package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardReport
import java.time.LocalDateTime

data class BoardReportDTO(
    val boardReportId: Long,
    val boardId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?
)

fun BoardReport.toDTO(): BoardReportDTO {
    return BoardReportDTO(
        boardReportId = boardReportId!!,
        boardId = boardId,
        userId = userId,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt,
    )
}
