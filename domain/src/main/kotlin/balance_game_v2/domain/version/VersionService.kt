package balance_game_v2.domain.version

import balance_game_v2.domain.announcement.dto.toDTO
import balance_game_v2.domain.version.dto.VersionDTO
import balance_game_v2.domain.version.dto.VersionPageDTO
import balance_game_v2.domain.version.dto.toDTO
import balance_game_v2.domain.version.repository.VersionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class VersionService(
    val versionRepository: VersionRepository
) {
    fun getVersion(): VersionDTO {
        return versionRepository.findTopByOrderByVersionIdDesc().toDTO()
    }

    fun getVersionPage(
        query: String?,
        page: Int,
        size: Int,
    ): VersionPageDTO {
        val pageable = PageRequest.of(page, size)

        val versions = versionRepository.search(query, pageable)

        return VersionPageDTO(
            versions = versions.content.map { it.toDTO() },
            totalPage = versions.totalPages
        )
    }
}
