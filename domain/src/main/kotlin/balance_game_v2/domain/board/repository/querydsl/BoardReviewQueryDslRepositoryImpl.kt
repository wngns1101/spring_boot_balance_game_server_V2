package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReview
import balance_game_v2.domain.board.entity.QBoardReview.boardReview
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class BoardReviewQueryDslRepositoryImpl : BoardReviewQueryDslRepository, QuerydslRepositorySupport(BoardReview::class.java) {
    override fun search(boardId: Long, boardReviewIds: List<Long>?): List<BoardReview> {
        return from(boardReview)
            .where(boardReview.boardId.eq(boardId), filteringBoardReviews(boardReviewIds))
            .fetch()
    }

    override fun searchRecommendReview(boardReviewReportIds: List<Long>?, boardReportIds: List<Long>?): List<BoardReview> {
        return from(boardReview)
            .where(filteringBoards(boardReportIds), filteringBoardReviews(boardReviewReportIds))
            .limit(5)
            .orderBy(Expressions.numberTemplate(Double::class.java, "function('rand')").asc())
            .fetch()
    }
}

private fun filteringBoards(boardReportsIds: List<Long>?): BooleanExpression? {
    if (boardReportsIds == null) return null
    return boardReview.boardId.notIn(boardReportsIds)
}

private fun filteringBoardReviews(boardReviewReportIds: List<Long>?): BooleanExpression? {
    if (boardReviewReportIds == null) return null
    return boardReview.boardReviewId.notIn(boardReviewReportIds)
}
