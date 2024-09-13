package domain.board.repository.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import domain.board.entity.BoardReview
import domain.board.entity.QBoard.board
import domain.board.entity.QBoardReview.boardReview
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class BoardReviewQueryDslRepositoryImpl : BoardReviewQueryDslRepository, QuerydslRepositorySupport(BoardReview::class.java) {
    override fun search(boardId: Long, boardReviewIds: List<Long>?): List<BoardReview> {
        return from(boardReview)
            .where(boardReview.boardId.eq(boardId), filteringBoards(boardReviewIds))
            .fetch()
    }
}

private fun filteringBoards(boardReportsIds: List<Long>?): BooleanExpression? {
    if (boardReportsIds == null) return null
    return board.boardId.notIn(boardReportsIds)
}