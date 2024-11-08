package balance_game_v2.domain.theme.repository

import balance_game_v2.domain.theme.entity.Theme
import balance_game_v2.domain.theme.repository.querydsl.ThemeQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ThemeRepository : JpaRepository<Theme, Long>, ThemeQueryDslRepository
