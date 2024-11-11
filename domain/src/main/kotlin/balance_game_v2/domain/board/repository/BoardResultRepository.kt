package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardResult
import org.springframework.data.jpa.repository.JpaRepository

interface BoardResultRepository : JpaRepository<BoardResult, Long> {
    fun findAllByBoardId(boardId: Long): List<BoardResult>
    fun countByBoardContentId(boardContentId: Long): Long
    fun findAllByBoardContentItemIdIn(boardContentItemIds: List<Long>): List<BoardResult>
    fun findAllByBoardIdAndUserId(boardId: Long, userId: Long): List<BoardResult>
    fun findAllByUserId(userId: Long): List<BoardResult>
    fun deleteByUserId(userId: Long)
}
