package domain.board.repository

import domain.board.entity.BoardContentItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardContentItemRepository : JpaRepository<BoardContentItem, Int>
