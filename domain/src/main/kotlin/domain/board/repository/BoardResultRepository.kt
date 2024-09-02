package domain.board.repository

import domain.board.entity.BoardResult
import org.springframework.data.jpa.repository.JpaRepository

interface BoardResultRepository : JpaRepository<BoardResult, Long> {
//    fun findAllByBoardId(boardId: Long): List<BoardResult>
    fun countByBoardContentId(boardContentId: Long): Long
    fun findAllByBoardContentItemIdIn(boardContentItemIds: List<Long>): List<BoardResult>
    fun findAllByBoardIdAndUserId(boardId: Long, userId: Long): List<BoardResult>
    fun findByUserId(userId: Long): List<BoardResult>
}
