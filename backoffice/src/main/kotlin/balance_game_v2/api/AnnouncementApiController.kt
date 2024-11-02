package balance_game_v2.api

import balance_game_v2.api.http.req.CreateAnnouncementRequestDTO
import balance_game_v2.api.http.req.ModifyAnnouncementRequestDTO
import balance_game_v2.api.http.req.toCommand
import balance_game_v2.domain.announcement.AnnouncementService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/backoffice/v2/announcement/api")
class AnnouncementApiController(
    private val announcementService: AnnouncementService,
) {
    @PutMapping("/announcement")
    fun modifyAnnouncement(
        @RequestBody request: ModifyAnnouncementRequestDTO,
    ) {
        announcementService.modifyAnnouncement(request.toCommand())
    }

    @DeleteMapping("/announcement/{announcementId}")
    fun deleteAnnouncement(
        @PathVariable announcementId: Long,
    ) {
        announcementService.deleteAnnouncement(announcementId)
    }

    @PostMapping("/announcement")
    fun createAnnouncement(
        @RequestBody request: CreateAnnouncementRequestDTO
    ) {
        announcementService.createAnnouncement(request.toCommand())
    }
}
