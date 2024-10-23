package balance_game_v2.domain.theme

import balance_game_v2.domain.theme.dto.ThemeDTO
import balance_game_v2.domain.theme.dto.ThemePageDTO
import balance_game_v2.domain.theme.dto.toDTO
import balance_game_v2.domain.theme.repository.ThemeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ThemeService(
    val themeRepository: ThemeRepository,
) {
    fun getThemes(): List<ThemeDTO> {
        return themeRepository.findAll().map { it.toDTO() }
    }

    fun getThemePage(
        query: String?,
        page: Int,
        size: Int,
    ): ThemePageDTO {
        val pageable = PageRequest.of(page, size)

        val themes = themeRepository.search(query, pageable)

        return ThemePageDTO(
            themes = themes.content.map { it.toDTO() },
            totalPage = themes.totalPages
        )
    }
}
