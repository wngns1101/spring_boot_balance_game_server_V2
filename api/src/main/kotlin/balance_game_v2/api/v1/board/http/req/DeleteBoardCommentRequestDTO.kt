package balance_game_v2.api.v1.board.http.req

import domain.board.dto.DeleteBoardCommentCommand

data class DeleteBoardCommentRequestDTO (
    val commentId: Long,
)

fun DeleteBoardCommentRequestDTO.toCommand(): DeleteBoardCommentCommand {
    return DeleteBoardCommentCommand(
        commentId = commentId,
    )
}