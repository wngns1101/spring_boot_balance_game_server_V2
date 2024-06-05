package balance_game_v2.api.v1.board.http.req

import domain.board.dto.ModifyBoardContentCommand

data class ModifyBoardCommentRequestDTO (
    val commentId: Long,
    val content: String,
)

fun ModifyBoardCommentRequestDTO.toCommand(): ModifyBoardContentCommand {
    return ModifyBoardContentCommand(
        commentId = commentId,
        content = content
    )
}