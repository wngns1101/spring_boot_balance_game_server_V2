package domain.board.repository

import domain.board.entity.BoardEvaluationHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardEvaluationHistoryRepository : JpaRepository<BoardEvaluationHistory, Long> {
    fun findByBoardIdAndUserIdAndIsLikeTrue(boardId: Long, userId: Long): BoardEvaluationHistory?
    fun findByBoardIdAndUserIdAndIsDislikeTrue(boardId: Long, userId: Long): BoardEvaluationHistory?
}
