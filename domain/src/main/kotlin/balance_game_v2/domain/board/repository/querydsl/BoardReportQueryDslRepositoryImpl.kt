package balance_game_v2.domain.board.repository.querydsl

import balance_game_v2.domain.board.entity.BoardReport
import balance_game_v2.domain.board.entity.QBoardReport.boardReport
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class BoardReportQueryDslRepositoryImpl : BoardReportQueryDslRepository, QuerydslRepositorySupport(BoardReport::class.java) {

    override fun search(query: String?, pageable: Pageable): Page<BoardReport> {
        val result = from(boardReport)
            .where(searchCondition(query))
            .offset(pageable.offset)
            .limit((pageable.pageSize).toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }

    private fun searchCondition(query: String?): BooleanExpression? {
        if (query.isNullOrBlank()) return null
        return boardReport.content.contains(query)
    }
}
