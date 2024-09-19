package balance_game_v2.domain.version.dto

import balance_game_v2.domain.version.entity.Version

class VersionDTO(
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String,
)

fun Version.toDTO() = VersionDTO(currentVersion, minimumVersion, preferVersion)
