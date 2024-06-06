package domain.board.repository

import domain.board.entity.BoardComment
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCommentRepository : JpaRepository<BoardComment, Long> {
    fun findAllByBoardId(boardId: Long): List<BoardComment>
}
