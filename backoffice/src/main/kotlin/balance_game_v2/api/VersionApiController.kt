package balance_game_v2.api

import balance_game_v2.api.http.req.CreateVersionRequestDTO
import balance_game_v2.api.http.req.ModifyVersionRequestDTO
import balance_game_v2.api.http.req.toCommand
import balance_game_v2.domain.version.VersionService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/backoffice/v2/version/api")
class VersionApiController(
    private val versionService: VersionService,
) {
    @PutMapping("/version")
    fun modifyTheme(
        @RequestBody request: ModifyVersionRequestDTO,
    ) {
        versionService.modifyVersion(request.toCommand())
    }

    @PostMapping("/version")
    fun createVersion(
        @RequestBody request: CreateVersionRequestDTO
    ) {
        versionService.createVersion(request.toCommand())
    }

    @DeleteMapping("/versions/{versionId}")
    fun deleteVersion(
        @PathVariable versionId: Long,
    ) {
        versionService.deleteVersion(versionId)
    }
}
