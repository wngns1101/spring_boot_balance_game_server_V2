package balance_game_v2.domain.version.repository.querydsl

import balance_game_v2.domain.version.entity.Version
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VersionQueryDslRepository {
    fun search(query: String?, pageable: Pageable): Page<Version>
}
