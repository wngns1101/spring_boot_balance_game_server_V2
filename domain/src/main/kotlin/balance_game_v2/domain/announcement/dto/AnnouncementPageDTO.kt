package balance_game_v2.domain.announcement.dto

data class AnnouncementPageDTO(
    val announcements: List<AnnouncementDTO>,
    val totalPage: Int
)
