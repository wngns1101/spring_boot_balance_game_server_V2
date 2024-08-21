package domain.board.repository

import domain.board.entity.BoardReviewReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardReviewReportRepository : JpaRepository<BoardReviewReport, Long> {
    fun findAllByUserId(userId: Long): List<BoardReviewReport>
}
