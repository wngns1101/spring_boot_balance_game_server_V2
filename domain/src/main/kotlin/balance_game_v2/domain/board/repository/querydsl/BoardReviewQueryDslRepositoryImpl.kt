package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReview
import balance_game_v2.domain.board.entity.QBoardReview.boardReview
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class BoardReviewQueryDslRepositoryImpl : BoardReviewQueryDslRepository, QuerydslRepositorySupport(BoardReview::class.java) {
    override fun search(boardId: Long, combinedUserIds: List<Long>): List<BoardReview> {
        return from(boardReview)
            .where(boardReview.boardId.eq(boardId), filteringUsers(combinedUserIds))
            .fetch()
    }

    override fun searchRecommendReview(): List<BoardReview> {
        return from(boardReview)
            .limit(5)
            .orderBy(Expressions.numberTemplate(Double::class.java, "function('rand')").asc())
            .fetch()
    }

    override fun searchRecommendReviewByUserId(
        myBoardIds: List<Long>,
        myBoardReviewIds: List<Long>,
        combinedUserIds: List<Long>,
    ): List<BoardReview> {
        return from(boardReview)
            .where(filteringBoards(myBoardIds), filteringBoardReviews(myBoardReviewIds), filteringUsers(combinedUserIds))
            .limit(5)
            .orderBy(Expressions.numberTemplate(Double::class.java, "function('rand')").asc())
            .fetch()
    }
}

private fun filteringBoards(boardIds: List<Long>): BooleanExpression? {
    if (boardIds.isEmpty()) return null
    return boardReview.boardId.notIn(boardIds)
}

private fun filteringBoardReviews(boardIds: List<Long>): BooleanExpression? {
    if (boardIds.isEmpty()) return null
    return boardReview.boardReviewId.notIn(boardIds)
}

private fun filteringUsers(userIds: List<Long>): BooleanExpression? {
    if (userIds.isEmpty()) return null
    return boardReview.userId.notIn(userIds)
}
