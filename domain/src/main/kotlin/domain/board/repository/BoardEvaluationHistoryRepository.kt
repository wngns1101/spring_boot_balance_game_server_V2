package domain.board.repository

import domain.board.entity.BoardEvaluationHistory
import org.springframework.data.jpa.repository.JpaRepository

interface BoardEvaluationHistoryRepository : JpaRepository<BoardEvaluationHistory, Long>
