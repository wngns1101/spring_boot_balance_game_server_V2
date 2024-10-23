package balance_game_v2.domain.version.dto

import balance_game_v2.domain.version.entity.Version

class VersionDTO(
    val versionId: Long,
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String,
)

fun Version.toDTO() = VersionDTO(versionId!!, currentVersion, minimumVersion, preferVersion)
