package domain.board.dto

data class ModifyBoardContentCommand(
    val commentId: Long,
    val content: String,
)
