package domain.board.dto

import domain.board.entity.BoardComment

data class BoardCommentDTO(
    val boardCommentId: Long? = null,
    val boardId: Long,
    val userId: Long,
    val parentCommentId: Long? = null,
    val comment: String,
)

fun BoardComment.toDTO(): BoardCommentDTO {
    return BoardCommentDTO(
        boardCommentId = boardCommentId,
        boardId = boardId,
        userId = userId,
        parentCommentId = parentCommentId,
        comment = comment,
    )
}
