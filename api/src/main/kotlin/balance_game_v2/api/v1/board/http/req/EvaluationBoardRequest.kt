package balance_game_v2.api.v1.board.http.req

import domain.domain.board.dto.EvaluationBoardCommand

data class EvaluationBoardRequest(
    val isLike: Boolean,
)

fun EvaluationBoardRequest.toCommand(): EvaluationBoardCommand {
    return EvaluationBoardCommand(
        isLike = isLike,
    )
}
