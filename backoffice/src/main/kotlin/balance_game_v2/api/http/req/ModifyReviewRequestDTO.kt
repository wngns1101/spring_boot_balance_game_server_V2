package balance_game_v2.api.http.req

import balance_game_v2.domain.board.dto.ModifyBoardReviewCommandDTO

data class ModifyReviewRequestDTO(
    val boardReviewId: Long,
    val boardId: Long,
    val userId: Long,
    val title: String,
    val comment: String,
    val isLike: Boolean,
    val isDislike: Boolean,
    val keywords: String?,
)

fun ModifyReviewRequestDTO.toCommand(): ModifyBoardReviewCommandDTO {
    return ModifyBoardReviewCommandDTO(
        boardReviewId = boardReviewId,
        boardId = boardId,
        userId = userId,
        title = title,
        comment = comment,
        isLike = isLike,
        isDislike = isDislike,
        keywords = keywords
    )
}
