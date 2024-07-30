package domain.theme.model

import domain.theme.entity.Theme

data class ThemeDTO(
    val themeId: Long,
    val theme: String,
    val iconUrl: String,
)

fun Theme.toDTO(): ThemeDTO {
    return ThemeDTO(
        themeId = themeId!!,
        theme = theme,
        iconUrl = iconUrl,
    )
}
