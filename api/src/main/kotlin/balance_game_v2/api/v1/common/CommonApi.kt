package balance_game_v2.api.v1.common

import balance_game_v2.api.v1.common.application.CommonFacade
import balance_game_v2.api.v1.common.http.res.VersionResponseDTO
import balance_game_v2.config.COMMON_V2
import balance_game_v2.config.COMMON_V2_PREFIX
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = COMMON_V2)
@RestController
@RequestMapping(COMMON_V2_PREFIX)
class CommonApi(
    val commonFacade: CommonFacade
) {
    @Operation(summary = "[공통-001] 버전 조회")
    @GetMapping("/version")
    fun getVersion(): VersionResponseDTO {
        return VersionResponseDTO(commonFacade.getVersion())
    }
}
