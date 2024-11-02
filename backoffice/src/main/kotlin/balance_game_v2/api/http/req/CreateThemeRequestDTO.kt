package balance_game_v2.api.http.req

import balance_game_v2.domain.theme.dto.CreateThemeCommandDTO

data class CreateThemeRequestDTO(
    val theme: String,
    val iconUrl: String
)

fun CreateThemeRequestDTO.toCommand(): CreateThemeCommandDTO {
    return CreateThemeCommandDTO(
        theme = theme,
        iconUrl = iconUrl
    )
}
