package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.BoardReviewKeyword

data class BoardReviewKeywordDTO(
    val boardReviewKeywordId: Long,
    val boardReviewId: Long,
    val userId: Long,
    val keyword: String,
)

fun BoardReviewKeyword.toDTO(): BoardReviewKeywordDTO {
    return BoardReviewKeywordDTO(
        boardReviewKeywordId = boardReviewKeywordId!!,
        boardReviewId = boardReviewId,
        userId = userId,
        keyword = keyword,
    )
}
