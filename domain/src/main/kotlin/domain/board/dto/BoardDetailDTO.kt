package domain.board.dto

import domain.board.entity.Board
import domain.board.entity.QBoardComment.boardComment
import java.time.LocalDateTime

data class BoardDetailDTO(
    val boardId: Long,
    val writer: WriterDTO,
    val title: String,
    val content: String,
    val heartCount: Int,
    val viewCount: Int,
    val boardComment: List<BoardCommentDTO>?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

data class WriterDTO(
    val userId: Long,
    val nickName: String,
)

fun Board.toBoardDetail(writer: WriterDTO, boardComment: List<BoardCommentDTO>): BoardDetailDTO {
    return BoardDetailDTO(
        boardId = boardId!!,
        writer = writer,
        title = title,
        content = content,
        heartCount = heartCount,
        viewCount = viewCount,
        boardComment = boardComment,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
