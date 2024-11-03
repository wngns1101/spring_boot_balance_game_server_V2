package balance_game_v2.domain.theme.dto

import balance_game_v2.domain.theme.entity.Theme

data class ThemeDTO(
    val themeId: Long,
    val theme: String,
    val iconUrl: String?,
)

fun Theme.toDTO(): ThemeDTO {
    return ThemeDTO(
        themeId = themeId!!,
        theme = theme,
        iconUrl = iconUrl,
    )
}
