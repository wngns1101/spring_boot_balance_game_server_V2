package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.BoardReviewBlock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardReviewBlockRepository : JpaRepository<BoardReviewBlock, Long> {
    fun findAllByUserId(userId: Long): List<BoardReviewBlock>
}
