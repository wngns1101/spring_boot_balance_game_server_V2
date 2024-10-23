package balance_game_v2.controller

import balance_game_v2.domain.announcement.AnnouncementService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/backoffice/v2/announcement")
class AnnouncementController(
    private val announcementService: AnnouncementService,
) {
    @GetMapping("/announcements")
    fun getAnnouncementPage(
        @RequestParam(value = "query") query: String?,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        model: Model,
    ): String {
        val adjustedPage = page - 1

        val announcementPage = announcementService.getAnnouncementPage(query, adjustedPage, size)

        model.addAttribute("announcements", announcementPage.announcements)
        model.addAttribute("currentPage", page)
        model.addAttribute("pageGroup", (page - 1) / 5)
        model.addAttribute("totalPage", announcementPage.totalPage)

        return "announcements"
    }
}
