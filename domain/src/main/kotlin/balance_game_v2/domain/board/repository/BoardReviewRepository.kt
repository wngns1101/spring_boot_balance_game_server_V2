package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardReview
import balance_game_v2.domain.board.repository.querydsl.BoardReviewQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository

interface BoardReviewRepository : JpaRepository<BoardReview, Long>, BoardReviewQueryDslRepository {
    fun findAllByBoardId(boardId: Long): List<BoardReview>?
    fun findByBoardIdAndUserId(boardId: Long, userId: Long): BoardReview?
    fun existsByBoardIdAndUserId(boardId: Long, userId: Long): Boolean
    fun findAllByUserId(userId: Long): List<BoardReview>
    fun deleteByUserId(userId: Long)
}
