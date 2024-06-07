package domain.user.repository

import domain.user.entity.UserReport
import org.springframework.data.jpa.repository.JpaRepository

interface UserReportRepository : JpaRepository<UserReport, Long> {
    fun findAllByUserId(userId: Long): List<UserReport>
}
