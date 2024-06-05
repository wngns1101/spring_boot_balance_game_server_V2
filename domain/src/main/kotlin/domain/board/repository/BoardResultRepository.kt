package domain.board.repository

import domain.board.entity.BoardResult
import org.springframework.data.jpa.repository.JpaRepository

interface BoardResultRepository : JpaRepository<BoardResult, Long> {
    fun findAllByBoardId(boardId: Long): List<BoardResult>
}