package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardContentItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardContentItemRepository : JpaRepository<BoardContentItem, Long> {
    fun findAllByBoardContentIdIn(boardContentIds: List<Long>): List<BoardContentItem>
    fun deleteByBoardContentId(boardContentId: Long)
}
