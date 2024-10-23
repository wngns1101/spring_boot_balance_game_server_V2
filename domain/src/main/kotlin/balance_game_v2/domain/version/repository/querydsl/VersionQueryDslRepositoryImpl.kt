package balance_game_v2.domain.version.repository.querydsl

import balance_game_v2.domain.version.entity.QVersion.version
import balance_game_v2.domain.version.entity.Version
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Component

@Component
class VersionQueryDslRepositoryImpl :
    VersionQueryDslRepository,
    QuerydslRepositorySupport(Version::class.java) {
    override fun search(query: String?, pageable: Pageable): Page<Version> {
        val result = from(version)
            .offset(pageable.offset)
            .limit((pageable.pageSize).toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }
}
