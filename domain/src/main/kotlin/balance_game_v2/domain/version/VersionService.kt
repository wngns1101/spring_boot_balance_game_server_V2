package balance_game_v2.domain.version

import balance_game_v2.domain.board.exception.NotFoundException
import balance_game_v2.domain.version.dto.CreateVersionCommandDTO
import balance_game_v2.domain.version.dto.ModifyVersionCommandDTO
import balance_game_v2.domain.version.dto.VersionDTO
import balance_game_v2.domain.version.dto.VersionPageDTO
import balance_game_v2.domain.version.dto.toDTO
import balance_game_v2.domain.version.entity.Version
import balance_game_v2.domain.version.repository.VersionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    fun getVersionById(
        versionId: Long,
    ): VersionDTO {
        val version = versionRepository.findById(versionId).orElseThrow { NotFoundException() }
        return version.toDTO()
    }

    @Transactional
    fun modifyVersion(
        command: ModifyVersionCommandDTO
    ) {
        val version = versionRepository.findById(command.versionId).orElseThrow { NotFoundException() }
        version.currentVersion = command.currentVersion
        version.minimumVersion = command.minimumVersion
        version.preferVersion = command.preferVersion
    }

    @Transactional
    fun createVersion(
        command: CreateVersionCommandDTO
    ) {
        Version(
            currentVersion = command.currentVersion,
            minimumVersion = command.minimumVersion,
            preferVersion = command.preferVersion,
        ).let {
            versionRepository.save(it)
        }
    }

    @Transactional
    fun deleteVersion(
        versionId: Long,
    ) {
        val version = versionRepository.findById(versionId).orElseThrow { NotFoundException() }
        versionRepository.delete(version)
    }
}
