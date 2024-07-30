package balance_game_v2.api.v1.common.http.res

import domain.theme.model.ThemeDTO

data class ThemeResponseDTO(
    val themes: List<ThemeDTO>,
)
