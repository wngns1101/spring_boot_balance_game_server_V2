package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReviewKeyword

interface BoardReviewKeywordQueryDslRepository {
    fun previewBoardReviewKeyword(boardReviewIds: List<Long>): List<BoardReviewKeyword>
}
