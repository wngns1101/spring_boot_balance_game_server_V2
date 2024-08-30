package balance_game_v2.api.v1.board.http.req

import domain.board.dto.CreateBoardReviewCommand

data class CreateBoardReviewRequestDTO(
    val title: String,
    val comment: String,
    val keywords: List<String>,
    val isLike: Boolean?,
    val isDislike: Boolean?,
)

fun CreateBoardReviewRequestDTO.toCommand(): CreateBoardReviewCommand {
    return CreateBoardReviewCommand(
        title = title,
        comment = comment,
        keywords = keywords,
        isLike = isLike ?: false,
        isDislike = isDislike ?: false,
    )
}
