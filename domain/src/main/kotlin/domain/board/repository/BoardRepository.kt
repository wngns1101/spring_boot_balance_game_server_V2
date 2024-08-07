package domain.board.repository

import domain.board.entity.Board
import domain.board.repository.querydsl.BoardQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long>, BoardQueryDslRepository {
    fun findByBoardIdAndDeletedAtIsNull(boardId: Long): Board?
    fun countBoardByUserId(userId: Long): Int
}
