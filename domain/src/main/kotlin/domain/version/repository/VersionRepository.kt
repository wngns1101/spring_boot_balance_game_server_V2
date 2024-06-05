package domain.version.repository

import domain.version.entity.Version
import org.springframework.data.jpa.repository.JpaRepository

interface VersionRepository: JpaRepository<Version, Long> {
    fun findTopByOrderByVersionIdDesc(): Version
}
