package balance_game_v2.domain.announcement

import balance_game_v2.domain.announcement.dto.AnnouncementDTO
import balance_game_v2.domain.announcement.dto.AnnouncementSimpleDTO
import balance_game_v2.domain.announcement.dto.toDTO
import balance_game_v2.domain.announcement.dto.toSimpleDTO
import balance_game_v2.domain.announcement.model.SearchCondition
import balance_game_v2.domain.announcement.repository.AnnouncementRepository
import balance_game_v2.domain.board.exception.NotFoundException
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
