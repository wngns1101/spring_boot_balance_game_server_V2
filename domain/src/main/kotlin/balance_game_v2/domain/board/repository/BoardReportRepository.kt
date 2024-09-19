package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardReportRepository : JpaRepository<BoardReport, Long> {
    fun findAllByUserId(userId: Long): List<BoardReport>
    fun deleteByUserId(userId: Long)
}
