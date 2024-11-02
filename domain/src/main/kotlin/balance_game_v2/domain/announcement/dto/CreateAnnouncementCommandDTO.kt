package balance_game_v2.domain.announcement.dto

data class CreateAnnouncementCommandDTO(
    val title: String,
    val content: String,
    val type: String
)
