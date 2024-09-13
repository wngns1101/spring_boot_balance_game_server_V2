package domain.board.repository.querydsl

import domain.board.entity.BoardReview
import domain.board.entity.QBoardReview.boardReview
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class BoardReviewQueryDslRepositoryImpl : BoardReviewQueryDslRepository, QuerydslRepositorySupport(BoardReview::class.java) {
    override fun search(boardId: Long, boardReviewIds: List<Long>?): List<BoardReview> {
        return from(boardReview)
            .where(boardReview.boardId.eq(boardId), boardReview.boardReviewId.notIn(boardReviewIds))
            .fetch()
    }
}
