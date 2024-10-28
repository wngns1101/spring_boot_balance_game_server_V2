package balance_game_v2.api.v2.common

import balance_game_v2.api.v2.common.application.CommonFacade
import balance_game_v2.api.v2.common.http.res.AnnouncementDetailResponseDTO
import balance_game_v2.api.v2.common.http.res.AnnouncementResponseDTO
import balance_game_v2.api.v2.common.http.res.ThemeResponseDTO
import balance_game_v2.api.v2.common.http.res.VersionResponseDTO
import balance_game_v2.client.COMMON_V2
import balance_game_v2.client.COMMON_V2_PREFIX
import balance_game_v2.domain.announcement.model.SearchCondition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = COMMON_V2)
@RestController
@RequestMapping(COMMON_V2_PREFIX)
class CommonApi(
    val commonFacade: CommonFacade,
) {
    @Operation(summary = "[공통-001] 버전 조회")
    @GetMapping("/version")
    fun getVersion(): VersionResponseDTO {
        return VersionResponseDTO(commonFacade.getVersion())
    }

    @Operation(summary = "[공통-002] 테마 조회")
    @GetMapping("/themes")
    fun getThemes(): ThemeResponseDTO {
        return ThemeResponseDTO(commonFacade.getThemes())
    }

    @Operation(summary = "[공통-003] 공지사항 조회")
    @GetMapping("/announcements")
    fun getAnnouncement(
        @RequestParam("searchCondition") condition: SearchCondition,
    ): AnnouncementResponseDTO {
        return AnnouncementResponseDTO(commonFacade.getAnnouncement(condition))
    }

    @Operation(summary = "[공통-004] 공지사항 상세 조회")
    @GetMapping("/announcements/{announcementId}")
    fun getAnnouncementDetail(
        @PathVariable("announcementId") announcementId: Long,
    ): AnnouncementDetailResponseDTO {
        return AnnouncementDetailResponseDTO(commonFacade.getAnnouncementDetail(announcementId))
    }
}
