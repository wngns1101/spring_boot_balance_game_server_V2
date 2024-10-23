package balance_game_v2.domain.user.repository.querydsl

import balance_game_v2.domain.user.entity.QUser.user
import balance_game_v2.domain.user.entity.User
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class UserQueryDslRepositoryImpl :
    UserQueryDslRepository,
    QuerydslRepositorySupport(User::class.java) {
    override fun search(query: String?, pageable: Pageable): Page<User> {
        val result = from(user)
            .where(searchCondition(query))
            .offset(pageable.offset)
            .limit((pageable.pageSize).toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }

    private fun searchCondition(query: String?): BooleanExpression? {
        if (query.isNullOrBlank()) return null
        return user.realName.eq(query)
    }
}
