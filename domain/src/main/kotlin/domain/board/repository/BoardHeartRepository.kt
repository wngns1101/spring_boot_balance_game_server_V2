package domain.board.repository

import domain.board.entity.BoardHeart
import org.springframework.data.jpa.repository.JpaRepository

interface BoardHeartRepository: JpaRepository<BoardHeart, Long> {
    fun existsByBoardIdAndUserId(boardId: Long, userId: Long): Boolean
    fun findByBoardIdAndUserId(boardId: Long, userId: Long): BoardHeart
}