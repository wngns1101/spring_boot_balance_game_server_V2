package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardReviewKeyword
import balance_game_v2.domain.board.repository.querydsl.BoardReviewKeywordQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository

interface BoardReviewKeywordRepository : JpaRepository<BoardReviewKeyword, Long>, BoardReviewKeywordQueryDslRepository {
    fun findAllByBoardReviewId(boardReviewId: Long): List<BoardReviewKeyword>
    fun findAllByBoardReviewIdIn(boardReviewIds: List<Long>): List<BoardReviewKeyword>
    fun deleteByUserId(userId: Long)
}
