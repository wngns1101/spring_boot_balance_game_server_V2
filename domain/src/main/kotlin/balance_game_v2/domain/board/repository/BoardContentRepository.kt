package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardContent
import org.springframework.data.jpa.repository.JpaRepository

interface BoardContentRepository : JpaRepository<BoardContent, Long> {
    fun findAllByBoardId(boardId: Long): List<BoardContent>
    fun deleteByBoardId(boardId: Long): BoardContent
    fun findAllByBoardIdIn(boardIds: List<Long>): List<BoardContent>
}
