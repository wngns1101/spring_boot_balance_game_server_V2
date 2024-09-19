package balance_game_v2.domain.version

import balance_game_v2.domain.version.dto.VersionDTO
import balance_game_v2.domain.version.dto.toDTO
import balance_game_v2.domain.version.repository.VersionRepository
import org.springframework.stereotype.Service

@Service
class VersionService(
    val versionRepository: VersionRepository
) {
    fun getVersion(): VersionDTO {
        return versionRepository.findTopByOrderByVersionIdDesc().toDTO()
    }
}
