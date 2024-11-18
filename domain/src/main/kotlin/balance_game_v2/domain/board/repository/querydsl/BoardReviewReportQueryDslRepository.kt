package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReviewReport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardReviewReportQueryDslRepository {
    fun search(query: String?, pageable: Pageable): Page<BoardReviewReport>
}
