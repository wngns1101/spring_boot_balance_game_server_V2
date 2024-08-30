package domain.board.repository.querydsl

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
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
        themeId: Long?,
        boardReportsIds: List<Long>?
    ): Page<Board> {
        val result = from(board)
            .where(searchCondition(query), searchForThemeId(themeId), filteringBoards(boardReportsIds))
            .orderBy(*sortCondition(sortCondition))
            .offset(pageable.offset)
            .limit((pageable.pageSize).toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }

    override fun todayRecommendGame(): List<Board> {
        val maxViewCount = from(board)
            .orderBy(board.viewCount.desc())
            .limit(1)
            .fetchOne()

        return from(board)
            .where(board.viewCount.eq(maxViewCount.viewCount))
            .fetch()
    }

    override fun relatedBoards(boardId: Long, themeId: Long, boardReportsIds: List<Long>?): List<Board> {
        return from(board)
            .where(
                board.boardId.ne(boardId),
                board.themeId.eq(themeId),
                filteringBoards(boardReportsIds)
            )
            .orderBy(Expressions.numberTemplate(Double::class.java, "function('rand')").asc())
            .limit(10)
            .fetch()
    }
}

private fun searchCondition(query: String?): BooleanExpression? {
    if (query.isNullOrBlank()) return null
    return board.title.contains(query)
}

private fun searchForThemeId(themeId: Long?): BooleanExpression? {
    if (themeId == null) return null
    return board.themeId.eq(themeId)
}

private fun filteringBoards(boardReportsIds: List<Long>?): BooleanExpression? {
    if (boardReportsIds == null) return null
    return board.boardId.notIn(boardReportsIds)
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
