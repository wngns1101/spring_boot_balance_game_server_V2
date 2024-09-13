package domain.board.repository.querydsl

import domain.board.entity.BoardReview

interface BoardReviewQueryDslRepository {
    fun search(boardId: Long, boardReviewIds: List<Long>?): List<BoardReview>
}
