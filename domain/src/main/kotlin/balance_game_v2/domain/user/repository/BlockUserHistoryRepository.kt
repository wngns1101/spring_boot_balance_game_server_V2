package balance_game_v2.domain.user.repository

import balance_game_v2.domain.user.entity.BlockUserHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockUserHistoryRepository : JpaRepository<BlockUserHistory, Long> {
    fun findByUserId(userId: Long): BlockUserHistory?
    fun deleteByUserId(userId: Long)
}
