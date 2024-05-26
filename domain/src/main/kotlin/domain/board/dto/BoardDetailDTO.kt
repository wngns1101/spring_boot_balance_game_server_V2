package domain.board.dto

import domain.board.entity.Board
import domain.user.dto.UserDTO
import java.time.LocalDateTime

data class BoardDetailDTO(
    val boardId: Long,
    val writer: WriterDTO,
    val title: String,
    val content: String,
    val heartCount: Int,
    val viewCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

data class WriterDTO(
    val userId: Long,
    val nickName: String,
)

fun Board.toBoardDetail(writer: WriterDTO): BoardDetailDTO {
    return BoardDetailDTO(
        boardId = boardId!!,
        writer = writer,
        title = title,
        content = content,
        heartCount = heartCount,
        viewCount = viewCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}