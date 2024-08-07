package domain.announcement

import domain.announcement.dto.AnnouncementSimpleDTO
import domain.announcement.dto.toSimpleDTO
import domain.announcement.model.SearchCondition
import domain.announcement.repository.AnnouncementRepository
import domain.board.exception.NotFoundException
import domain.domain.announcement.dto.AnnouncementDTO
import domain.domain.announcement.dto.toDTO
import jakarta.transaction.Transactional
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
}
