package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardReportQueryDslRepository {
    fun search(query: String?, pageable: Pageable): Page<BoardReport>
}
