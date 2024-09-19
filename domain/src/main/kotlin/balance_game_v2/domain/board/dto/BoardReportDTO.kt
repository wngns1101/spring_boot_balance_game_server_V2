package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardReport
import java.time.LocalDateTime

data class BoardReportDTO(
    val boardReportId: Long,
    val boardId: Long,
    val userId: Long,
    val createdAt: LocalDateTime,
)

fun BoardReport.toDTO(): BoardReportDTO {
    return BoardReportDTO(
        boardReportId = boardReportId!!,
        boardId = boardId,
        userId = userId,
        createdAt = createdAt
    )
}
