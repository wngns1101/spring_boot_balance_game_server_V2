package balance_game_v2.api.v1.common.application

import domain.version.VersionService
import domain.version.dto.VersionDTO
import org.springframework.stereotype.Component

@Component
class CommonFacade(
    val versionService: VersionService
){
    fun getVersion(): VersionDTO {
        return versionService.getVersion()
    }
}
