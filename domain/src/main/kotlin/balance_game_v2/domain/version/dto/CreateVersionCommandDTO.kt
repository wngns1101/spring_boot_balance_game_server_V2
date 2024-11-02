package balance_game_v2.domain.version.dto

data class CreateVersionCommandDTO(
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String
)
