package balance_game_v2.domain.board.repository

import balance_game_v2.domain.board.entity.Board
import balance_game_v2.domain.board.repository.querydsl.BoardQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long>, BoardQueryDslRepository {
    fun findByBoardIdAndDeletedAtIsNull(boardId: Long): Board?
    fun countBoardByUserId(userId: Long): Int
    fun findAllByUserId(userId: Long): List<Board>
    fun findByBoardIdIn(boardIds: List<Long>): List<Board>
    fun deleteByUserId(userId: Long): Board
}
