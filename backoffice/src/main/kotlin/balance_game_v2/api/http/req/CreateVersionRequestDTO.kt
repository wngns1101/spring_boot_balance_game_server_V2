package balance_game_v2.api.http.req

import balance_game_v2.domain.version.dto.CreateVersionCommandDTO

data class CreateVersionRequestDTO(
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String,
)

fun CreateVersionRequestDTO.toCommand(): CreateVersionCommandDTO {
    return CreateVersionCommandDTO(
        currentVersion = currentVersion,
        minimumVersion = minimumVersion,
        preferVersion = preferVersion,
    )
}
