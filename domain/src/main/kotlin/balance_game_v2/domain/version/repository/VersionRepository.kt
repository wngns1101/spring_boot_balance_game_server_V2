package balance_game_v2.domain.version.repository

import balance_game_v2.domain.version.entity.Version
import balance_game_v2.domain.version.repository.querydsl.VersionQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository

interface VersionRepository : JpaRepository<Version, Long>, VersionQueryDslRepository {
    fun findTopByOrderByVersionIdDesc(): Version
}
