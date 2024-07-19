package domain.board.repository.querydsl

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import domain.board.entity.Board
import domain.board.entity.QBoard.board
import domain.board.model.BoardSortCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class BoardQueryDslRepositoryImpl :
    BoardQueryDslRepository,
    QuerydslRepositorySupport(Board::class.java) {
    override fun search(
        query: String?,
        pageable: Pageable,
        sortCondition: BoardSortCondition?,
        themeId: Long
    ): Page<Board> {
        val result = from(board)
            .where(searchCondition(query), board.themeId.eq(themeId))
            .orderBy(*sortCondition(sortCondition))
            .offset(pageable.offset)
            .limit((pageable.pageSize).toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }
}

private fun searchCondition(query: String?): BooleanExpression? {
    if (query.isNullOrBlank()) return null
    return board.title.contains(query)
}

private fun sortCondition(sortCondition: BoardSortCondition?): Array<OrderSpecifier<*>> {
    if (sortCondition == null) return emptyArray()
    return when (sortCondition) {
        BoardSortCondition.DATE -> {
            arrayOf(board.updatedAt.desc())
        }
        BoardSortCondition.LIKE -> {
            arrayOf(board.likeCount.desc())
        }
    }
}
