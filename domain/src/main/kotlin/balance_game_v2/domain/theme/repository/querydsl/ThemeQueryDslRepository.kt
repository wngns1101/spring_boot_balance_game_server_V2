package balance_game_v2.domain.theme.repository.querydsl

import balance_game_v2.domain.theme.entity.Theme
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ThemeQueryDslRepository {
    fun search(query: String?, pageable: Pageable): Page<Theme>
}
