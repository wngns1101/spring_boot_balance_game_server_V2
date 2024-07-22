package domain.board.repository

import domain.board.entity.BoardKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardKeywordRepository : JpaRepository<BoardKeyword, Int> {
    fun findAllByBoardId(boardId: Long): List<BoardKeyword>
}
