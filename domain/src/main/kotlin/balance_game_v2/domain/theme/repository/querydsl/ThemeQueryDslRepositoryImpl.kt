package balance_game_v2.domain.theme.repository.querydsl

import balance_game_v2.domain.theme.entity.QTheme.theme1
import balance_game_v2.domain.theme.entity.Theme
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class ThemeQueryDslRepositoryImpl :
    ThemeQueryDslRepository,
    QuerydslRepositorySupport(Theme::class.java) {
    override fun search(query: String?, pageable: Pageable): Page<Theme> {
        val result = from(theme1)
            .where(searchCondition(query))
            .offset(pageable.offset)
            .limit((pageable.pageSize).toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }

    private fun searchCondition(query: String?): BooleanExpression? {
        if (query.isNullOrBlank()) return null
        return theme1.theme.eq(query)
    }
}
