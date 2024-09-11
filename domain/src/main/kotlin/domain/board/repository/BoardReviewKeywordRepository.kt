package domain.board.repository

import domain.board.entity.BoardReviewKeyword
import domain.board.repository.querydsl.BoardReviewKeywordQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository

interface BoardReviewKeywordRepository : JpaRepository<BoardReviewKeyword, Long>, BoardReviewKeywordQueryDslRepository {
    fun findAllByBoardReviewId(boardReviewId: Long): List<BoardReviewKeyword>
    fun findAllByBoardReviewIdIn(boardReviewIds: List<Long>): List<BoardReviewKeyword>
    fun deleteByUserId(userId: Long)
}
