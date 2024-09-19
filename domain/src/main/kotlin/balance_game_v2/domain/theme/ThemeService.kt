package balance_game_v2.domain.theme

import balance_game_v2.domain.theme.model.ThemeDTO
import balance_game_v2.domain.theme.model.toDTO
import balance_game_v2.domain.theme.repository.ThemeRepository
import org.springframework.stereotype.Service

@Service
class ThemeService(
    val themeRepository: ThemeRepository,
) {
    fun getThemes(): List<ThemeDTO> {
        return themeRepository.findAll().map { it.toDTO() }
    }
}
