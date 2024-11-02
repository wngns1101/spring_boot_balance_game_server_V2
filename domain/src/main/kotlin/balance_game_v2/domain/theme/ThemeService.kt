package balance_game_v2.domain.theme

import balance_game_v2.client.S3Config
import balance_game_v2.domain.board.exception.NotFoundException
import balance_game_v2.domain.theme.dto.CreateThemeCommandDTO
import balance_game_v2.domain.theme.dto.ModifyThemeCommandDTO
import balance_game_v2.domain.theme.dto.ThemeDTO
import balance_game_v2.domain.theme.dto.ThemePageDTO
import balance_game_v2.domain.theme.dto.toDTO
import balance_game_v2.domain.theme.entity.Theme
import balance_game_v2.domain.theme.repository.ThemeRepository
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ThemeService(
    private val themeRepository: ThemeRepository,
    private val s3Config: S3Config,
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

    fun getThemeById(
        themeId: Long,
    ): ThemeDTO {
        val theme = themeRepository.findById(themeId).orElseThrow { NotFoundException() }
        return theme.toDTO()
    }

    fun uploadIcon(
        file: MultipartFile,
    ): String {
        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = file.size
        }

        val objectKey = "icons/${UUID.randomUUID()}-${file.originalFilename}"

        val putObjectRequest = PutObjectRequest(
            "balance-game-bucket",
            objectKey,
            file.inputStream,
            objectMetadata,
        )

        s3Config.amazonS3Client().putObject(putObjectRequest)

        val publicUrl = "https://balance-game-bucket.s3.amazonaws.com/$objectKey"

        return publicUrl
    }

    @Transactional
    fun modifyTheme(
        command: ModifyThemeCommandDTO
    ) {
        val theme = themeRepository.findById(command.themeId).orElseThrow { NotFoundException() }
        theme.theme = command.theme
        theme.iconUrl = command.iconUrl
    }

    @Transactional
    fun createTheme(
        command: CreateThemeCommandDTO
    ) {
        Theme(
            theme = command.theme,
            iconUrl = command.iconUrl,
        ).let {
            themeRepository.save(it)
        }
    }

    @Transactional
    fun deleteTheme(
        themeId: Long,
    ) {
        val theme = themeRepository.findById(themeId).orElseThrow { NotFoundException() }
        themeRepository.delete(theme)
    }
}
