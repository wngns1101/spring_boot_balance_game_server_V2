package domain.user.repository

import domain.user.entity.BlockUserHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockUserHistoryRepository : JpaRepository<BlockUserHistory, Long> {
    fun findByUserId(userId: Long): BlockUserHistory?
    fun deleteByUserId(userId: Long)
}
