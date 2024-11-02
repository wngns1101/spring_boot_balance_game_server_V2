package balance_game_v2.domain.announcement

import balance_game_v2.domain.announcement.dto.AnnouncementDTO
import balance_game_v2.domain.announcement.dto.AnnouncementPageDTO
import balance_game_v2.domain.announcement.dto.AnnouncementSimpleDTO
import balance_game_v2.domain.announcement.dto.CreateAnnouncementCommandDTO
import balance_game_v2.domain.announcement.dto.ModifyAnnouncementCommandDTO
import balance_game_v2.domain.announcement.dto.toDTO
import balance_game_v2.domain.announcement.dto.toSimpleDTO
import balance_game_v2.domain.announcement.entity.Announcement
import balance_game_v2.domain.announcement.model.SearchCondition
import balance_game_v2.domain.announcement.repository.AnnouncementRepository
import balance_game_v2.domain.board.exception.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class AnnouncementService(
    val announcementRepository: AnnouncementRepository,
) {
    fun getAnnouncement(condition: SearchCondition): List<AnnouncementSimpleDTO> {
        return when (condition) {
            SearchCondition.ALL -> announcementRepository.findAll().map { it.toSimpleDTO() }
            else -> announcementRepository.findAnnouncementByType(condition).map { it.toSimpleDTO() }
        }
    }

    @Transactional
    fun getAnnouncementDetail(announcementId: Long): AnnouncementDTO {
        val announcement = announcementRepository.findById(announcementId).orElseThrow { NotFoundException() }
        announcement.viewCount += 1

        return announcement.toDTO()
    }

    fun getAnnouncementPage(
        query: String?,
        page: Int,
        size: Int,
    ): AnnouncementPageDTO {
        val pageable = PageRequest.of(page, size)

        val announcements = announcementRepository.search(query, pageable)

        return AnnouncementPageDTO(
            announcements = announcements.content.map { it.toDTO() },
            totalPage = announcements.totalPages
        )
    }

    fun getAnnouncementByAdmin(
        announcementId: Long,
    ): AnnouncementDTO {
        val announcement = announcementRepository.findById(announcementId).orElseThrow { NotFoundException() }
        return announcement.toDTO()
    }

    @Transactional
    fun modifyAnnouncement(
        command: ModifyAnnouncementCommandDTO
    ) {
        val announcement = announcementRepository.findById(command.announcementId).orElseThrow { NotFoundException() }
        announcement.title = command.title
        announcement.content = command.content
        announcement.type = SearchCondition.valueOf(command.type)
    }

    @Transactional
    fun deleteAnnouncement(
        announcementId: Long,
    ) {
        val announcement = announcementRepository.findById(announcementId).orElseThrow { NotFoundException() }
        announcementRepository.delete(announcement)
    }

    @Transactional
    fun createAnnouncement(
        command: CreateAnnouncementCommandDTO
    ) {
        Announcement(
            title = command.title,
            content = command.content,
            type = SearchCondition.valueOf(command.type)
        ).let {
            announcementRepository.save(it)
        }
    }
}
