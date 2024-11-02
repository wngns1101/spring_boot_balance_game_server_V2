package balance_game_v2.api

import balance_game_v2.api.http.req.CreateThemeRequestDTO
import balance_game_v2.api.http.req.ModifyThemeRequestDTO
import balance_game_v2.api.http.req.toCommand
import balance_game_v2.domain.theme.ThemeService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/backoffice/v2/theme/api")
class ThemeApiController(
    private val themeService: ThemeService,
) {
    @PostMapping("/upload-icon")
    fun uploadIcon(
        @RequestPart("icon") image: MultipartFile,
    ): String {
        return themeService.uploadIcon(image)
    }

    @PutMapping("/theme")
    fun modifyTheme(
        @RequestBody request: ModifyThemeRequestDTO,
    ) {
        return themeService.modifyTheme(request.toCommand())
    }

    @DeleteMapping("/theme/{themeId}")
    fun modifyTheme(
        @PathVariable themeId: Long,
    ) {
        return themeService.deleteTheme(themeId)
    }

    @PostMapping("/theme")
    fun createIcon(
        @RequestBody request: CreateThemeRequestDTO
    ) {
        return themeService.createTheme(request.toCommand())
    }
}
