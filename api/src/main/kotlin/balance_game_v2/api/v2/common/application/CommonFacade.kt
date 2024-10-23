package balance_game_v2.api.v2.common.application

import balance_game_v2.domain.announcement.AnnouncementService
import balance_game_v2.domain.announcement.dto.AnnouncementDTO
import balance_game_v2.domain.announcement.dto.AnnouncementSimpleDTO
import balance_game_v2.domain.announcement.model.SearchCondition
import balance_game_v2.domain.theme.ThemeService
import balance_game_v2.domain.theme.dto.ThemeDTO
import balance_game_v2.domain.version.VersionService
import balance_game_v2.domain.version.dto.VersionDTO
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
