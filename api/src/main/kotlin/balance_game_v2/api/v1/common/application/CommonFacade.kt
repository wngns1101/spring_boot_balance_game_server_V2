package balance_game_v2.api.v1.common.application

import domain.theme.ThemeService
import domain.theme.model.ThemeDTO
import domain.version.VersionService
import domain.version.dto.VersionDTO
import org.springframework.stereotype.Component

@Component
class CommonFacade(
    val versionService: VersionService,
    val themeService: ThemeService,
) {

    fun getVersion(): VersionDTO {
        return versionService.getVersion()
    }

    fun getThemes(): List<ThemeDTO> {
        return themeService.getThemes()
    }
}
