package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardBlock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardBlockRepository : JpaRepository<BoardBlock, Long> {
    fun findAllByUserId(userId: Long): List<BoardBlock>
}
