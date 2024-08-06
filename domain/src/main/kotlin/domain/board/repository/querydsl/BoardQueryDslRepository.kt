package domain.board.repository.querydsl

import domain.board.entity.Board
import domain.board.model.BoardSortCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardQueryDslRepository {
    fun search(query: String?, pageable: Pageable, sortCondition: BoardSortCondition?, themeId: Long): Page<Board>
    fun todayRecommendGame(): List<Board>
}
