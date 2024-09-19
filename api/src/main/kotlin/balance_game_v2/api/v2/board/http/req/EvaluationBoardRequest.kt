package balance_game_v2.api.v2.board.http.req

import balance_game_v2.domain.board.dto.EvaluationBoardCommand

data class EvaluationBoardRequest(
    val isLike: Boolean,
)

fun EvaluationBoardRequest.toCommand(): EvaluationBoardCommand {
    return EvaluationBoardCommand(
        isLike = isLike,
    )
}
