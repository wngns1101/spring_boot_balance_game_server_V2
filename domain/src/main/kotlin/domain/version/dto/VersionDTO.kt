package domain.version.dto

import domain.version.entity.Version

class VersionDTO (
    val currentVersion: String,
    val minimumVersion: String,
    val preferVersion: String,
)

fun Version.toDTO() = VersionDTO(currentVersion, minimumVersion, preferVersion)