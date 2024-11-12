package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReview

interface BoardReviewQueryDslRepository {
    fun search(boardId: Long, combinedBoardReviewIds: List<Long>): List<BoardReview>
    fun searchRecommendReview(): List<BoardReview>
    fun searchRecommendReviewByUserId(
        combinedBoardIds: List<Long>,
    ): List<BoardReview>
}
