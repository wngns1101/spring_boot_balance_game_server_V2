package balance_game_v2.controller

import balance_game_v2.domain.version.VersionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/backoffice/v2/version")
class VersionController(
    private val versionService: VersionService
) {
    @GetMapping("/create-version")
    fun createVersion(): String {
        return "create-version"
    }

    @GetMapping("/versions")
    fun getVersionPage(
        @RequestParam(value = "query") query: String?,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        model: Model,
    ): String {
        val adjustedPage = page - 1

        val versionPage = versionService.getVersionPage(query, adjustedPage, size)

        model.addAttribute("versions", versionPage.versions)
        model.addAttribute("currentPage", page)
        model.addAttribute("pageGroup", (page - 1) / 5)
        model.addAttribute("totalPage", versionPage.totalPage)

        return "versions"
    }

    @GetMapping("/versions/{versionId}")
    fun getVersion(
        @PathVariable versionId: Long,
        model: Model,
    ): String {
        val version = versionService.getVersionById(versionId)

        model.addAttribute("version", version)

        return "version-detail"
    }
}
