package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReview

interface BoardReviewQueryDslRepository {
    fun search(boardId: Long, boardReviewIds: List<Long>?): List<BoardReview>
    fun searchRecommendReview(): List<BoardReview>
    fun searchRecommendReviewByUserId(
        myBoardIds: List<Long>,
        myBoardReviewIds: List<Long>,
        boardReviewReportIds: List<Long>,
        boardReportIds: List<Long>
    ): List<BoardReview>
}
