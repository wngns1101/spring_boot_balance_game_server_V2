package balance_game_v2.api.http.req

import balance_game_v2.domain.version.dto.ModifyVersionCommandDTO

data class ModifyVersionRequestDTO(
    val versionId: Long,
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String,
)

fun ModifyVersionRequestDTO.toCommand(): ModifyVersionCommandDTO {
    return ModifyVersionCommandDTO(
        versionId = versionId,
        currentVersion = currentVersion,
        minimumVersion = minimumVersion,
        preferVersion = preferVersion,
    )
}
