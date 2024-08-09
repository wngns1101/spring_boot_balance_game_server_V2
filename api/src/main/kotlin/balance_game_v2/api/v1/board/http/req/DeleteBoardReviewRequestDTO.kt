package balance_game_v2.api.v1.board.http.req

import domain.board.dto.DeleteBoardReviewCommand

data class DeleteBoardReviewRequestDTO(
    val boardReviewId: Long,
)

fun DeleteBoardReviewRequestDTO.toCommand(): DeleteBoardReviewCommand {
    return DeleteBoardReviewCommand(
        boardReviewId = boardReviewId,
    )
}
