package balance_game_v2.api.v2.board.http.req

import balance_game_v2.domain.board.dto.ModifyBoardReviewCommand

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
