package balance_game_v2.api.http.req

import balance_game_v2.domain.theme.dto.ModifyThemeCommandDTO

data class ModifyThemeRequestDTO(
    val themeId: Long,
    val theme: String,
    val iconUrl: String,
)

fun ModifyThemeRequestDTO.toCommand(): ModifyThemeCommandDTO {
    return ModifyThemeCommandDTO(
        themeId = themeId,
        theme = theme,
        iconUrl = iconUrl,
    )
}
