package balance_game_v2.api.v2.common.http.res

import balance_game_v2.domain.theme.dto.ThemeDTO

data class ThemeResponseDTO(
    val themes: List<ThemeDTO>,
)
