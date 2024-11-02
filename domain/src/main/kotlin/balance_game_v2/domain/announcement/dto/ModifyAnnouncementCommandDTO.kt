package balance_game_v2.domain.announcement.dto

class ModifyAnnouncementCommandDTO(
    val announcementId: Long,
    val title: String,
    val content: String,
    val type: String,
)
