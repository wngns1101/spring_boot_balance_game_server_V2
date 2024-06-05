package domain.board.repository

import domain.board.entity.BoardContent
import org.springframework.data.jpa.repository.JpaRepository

interface BoardContentRepository: JpaRepository<BoardContent, Long> {
    fun findAllByBoardId(boardId: Long): List<BoardContent>
}
