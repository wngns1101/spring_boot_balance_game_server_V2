package domain.version

import domain.version.dto.VersionDTO
import domain.version.dto.toDTO
import domain.version.repository.VersionRepository
import org.springframework.stereotype.Service

@Service
class VersionService(
    val versionRepository: VersionRepository
) {
    fun getVersion(): VersionDTO {
        return versionRepository.findTopByOrderByVersionIdDesc().toDTO()
    }
}
