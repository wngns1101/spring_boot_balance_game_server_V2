package balance_game_v2.domain.version.repository

import balance_game_v2.domain.version.entity.Version
import org.springframework.data.jpa.repository.JpaRepository

interface VersionRepository : JpaRepository<Version, Long> {
    fun findTopByOrderByVersionIdDesc(): Version
}
