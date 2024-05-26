package domain.board.dto

import domain.board.entity.Board
import java.time.LocalDateTime

data class PageBoardDTO (
    val boards: List<BoardListDTO>,
    val totalPage: Int,
)

data class BoardListDTO (
    val boardId: Long,
    val title: String,
    val viewCount: Int,
    val heartCount: Int,
    val updatedAt: LocalDateTime,
)

fun Board.toPageBoardDTO(): BoardListDTO {
    return BoardListDTO(
        boardId = boardId!!,
        title = title,
        viewCount = viewCount,
        heartCount = heartCount,
        updatedAt = updatedAt,
    )
}