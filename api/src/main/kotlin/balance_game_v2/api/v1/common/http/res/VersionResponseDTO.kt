package balance_game_v2.api.v1.common.http.res

import domain.version.dto.VersionDTO

data class VersionResponseDTO (
    val version: VersionDTO
)