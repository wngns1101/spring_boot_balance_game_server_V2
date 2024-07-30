package domain.theme

import domain.theme.model.ThemeDTO
import domain.theme.model.toDTO
import domain.theme.repository.ThemeRepository
import org.springframework.stereotype.Service

@Service
class ThemeService(
    val themeRepository: ThemeRepository,
) {
    fun getThemes(): List<ThemeDTO> {
        return themeRepository.findAll().map { it.toDTO() }
    }
}
