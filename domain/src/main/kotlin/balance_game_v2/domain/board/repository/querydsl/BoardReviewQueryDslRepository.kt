package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReview

interface BoardReviewQueryDslRepository {
    fun search(boardId: Long, boardReviewIds: List<Long>?): List<BoardReview>
}
