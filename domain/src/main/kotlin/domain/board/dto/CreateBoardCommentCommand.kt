package domain.board.dto

data class CreateBoardCommentCommand (
    val parentCommentId: Long? = null,
    val comment: String,
)
