package balance_game_v2.controller

import balance_game_v2.domain.theme.ThemeService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/backoffice/v2/theme")
class ThemeController(
    private val themeService: ThemeService,
) {
    @GetMapping("/create-theme")
    fun createTheme(): String {
        return "create-theme"
    }

    @GetMapping("/themes")
    fun getThemePage(
        @RequestParam(value = "query") query: String?,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        model: Model,
    ): String {
        val adjustedPage = page - 1

        val themePage = themeService.getThemePage(query, adjustedPage, size)

        model.addAttribute("themes", themePage.themes)
        model.addAttribute("currentPage", page)
        model.addAttribute("pageGroup", (page - 1) / 5)
        model.addAttribute("totalPage", themePage.totalPage)

        return "themes"
    }

    @GetMapping("/themes/{themeId}")
    fun getTheme(
        @PathVariable themeId: Long,
        model: Model,
    ): String {
        val theme = themeService.getThemeById(themeId)

        model.addAttribute("theme", theme)

        return "theme-detail"
    }
}
