package domain.board.repository

import domain.board.entity.BoardCommentReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardCommentReportRepository : JpaRepository<BoardCommentReport, Long> {
    fun findAllByUserId(userId: Long): List<BoardCommentReport>
}
