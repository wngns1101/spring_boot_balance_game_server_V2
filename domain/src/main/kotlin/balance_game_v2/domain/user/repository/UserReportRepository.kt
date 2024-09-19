package balance_game_v2.domain.user.repository

import balance_game_v2.domain.user.entity.UserReport
import org.springframework.data.jpa.repository.JpaRepository

interface UserReportRepository : JpaRepository<UserReport, Long> {
    fun findAllByUserId(userId: Long): List<UserReport>
}
