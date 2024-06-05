package balance_game_v2.api.v1.board.http.req

import domain.board.dto.CreateBoardCommentCommand

data class CreateBoardCommentRequestDTO (
    val parentCommentId: Long? = null,
    val comment: String,
)

fun CreateBoardCommentRequestDTO.toCommand(): CreateBoardCommentCommand {
    return CreateBoardCommentCommand(
        parentCommentId = parentCommentId,
        comment = comment,
    )
}