package domain.board.repository.querydsl

import domain.board.entity.BoardReviewKeyword

interface BoardReviewKeywordQueryDslRepository {
    fun previewBoardReviewKeyword(boardReviewIds: List<Long>): List<BoardReviewKeyword>
}
