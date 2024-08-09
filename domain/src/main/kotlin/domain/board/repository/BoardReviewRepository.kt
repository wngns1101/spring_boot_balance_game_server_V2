package domain.board.repository

import domain.board.entity.BoardReview
import org.springframework.data.jpa.repository.JpaRepository

interface BoardReviewRepository : JpaRepository<BoardReview, Long> {
    fun findAllByBoardId(boardId: Long): List<BoardReview>
}
