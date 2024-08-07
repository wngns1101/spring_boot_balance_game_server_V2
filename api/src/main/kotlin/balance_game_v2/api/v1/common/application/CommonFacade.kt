package balance_game_v2.api.v1.common.application

import domain.announcement.AnnouncementService
import domain.announcement.dto.AnnouncementSimpleDTO
import domain.announcement.model.SearchCondition
import domain.domain.announcement.dto.AnnouncementDTO
import domain.theme.ThemeService
import domain.theme.model.ThemeDTO
import domain.version.VersionService
import domain.version.dto.VersionDTO
import org.springframework.stereotype.Component

@Component
class CommonFacade(
    val versionService: VersionService,
    val themeService: ThemeService,
    val announcementService: AnnouncementService,
) {

    fun getVersion(): VersionDTO {
        return versionService.getVersion()
    }

    fun getThemes(): List<ThemeDTO> {
        return themeService.getThemes()
    }

    fun getAnnouncement(condition: SearchCondition): List<AnnouncementSimpleDTO> {
        return announcementService.getAnnouncement(condition)
    }

    fun getAnnouncementDetail(announcementId: Long): AnnouncementDTO {
        return announcementService.getAnnouncementDetail(announcementId)
    }
}
