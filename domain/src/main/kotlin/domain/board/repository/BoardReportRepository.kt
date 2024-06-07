package domain.board.repository

import domain.board.entity.BoardReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardReportRepository : JpaRepository<BoardReport, Long> {
    fun findAllByUserId(userId: Long): List<BoardReport>
}
