package balance_game_v2.api.v1.board.http.req

import domain.board.dto.ModifyBoardReviewCommand

data class ModifyBoardReviewRequestDTO(
    val boardReviewId: Long,
    val comment: String,
    val keywords: List<String>
)

fun ModifyBoardReviewRequestDTO.toCommand(): ModifyBoardReviewCommand {
    return ModifyBoardReviewCommand(
        boardReviewId = boardReviewId,
        comment = comment,
        keywords = keywords,
    )
}
