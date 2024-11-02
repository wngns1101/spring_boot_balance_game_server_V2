package balance_game_v2.domain.theme.dto

data class ModifyThemeCommandDTO(
    val themeId: Long,
    val theme: String,
    val iconUrl: String,
)
