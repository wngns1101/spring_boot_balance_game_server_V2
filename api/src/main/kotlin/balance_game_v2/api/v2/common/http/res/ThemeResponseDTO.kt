package balance_game_v2.api.v2.common.http.res

import balance_game_v2.domain.theme.model.ThemeDTO

data class ThemeResponseDTO(
    val themes: List<ThemeDTO>,
)
