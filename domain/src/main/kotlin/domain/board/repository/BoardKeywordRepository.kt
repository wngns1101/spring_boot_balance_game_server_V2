package domain.board.repository

import domain.board.entity.BoardKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardKeywordRepository : JpaRepository<BoardKeyword, Int> {
    fun findAllByBoardIdIn(boardIds: List<Long>): List<BoardKeyword>
}
