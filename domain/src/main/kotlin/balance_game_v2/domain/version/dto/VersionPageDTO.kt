package balance_game_v2.domain.version.dto

data class VersionPageDTO(
    val versions: List<VersionDTO>,
    val totalPage: Int
)
