package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardKeywordRepository : JpaRepository<BoardKeyword, Int> {
    fun findAllByBoardIdIn(boardIds: List<Long>): List<BoardKeyword>
}
