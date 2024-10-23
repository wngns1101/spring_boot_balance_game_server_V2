package balance_game_v2.domain.announcement.repository.querydsl

import balance_game_v2.domain.announcement.entity.Announcement
import balance_game_v2.domain.announcement.entity.QAnnouncement.announcement
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class AnnouncementQueryDslRepositoryImpl :
    AnnouncementQueryDslRepository,
    QuerydslRepositorySupport(Announcement::class.java) {
    override fun search(query: String?, pageable: Pageable): Page<Announcement> {
        val result = from(announcement)
            .where(searchCondition(query))
            .offset(pageable.offset)
            .limit((pageable.pageSize).toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }

    private fun searchCondition(query: String?): BooleanExpression? {
        if (query.isNullOrBlank()) return null
        return announcement.title.eq(query)
    }
}
