package domain.board.dto

import domain.board.entity.BoardReviewKeyword

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
