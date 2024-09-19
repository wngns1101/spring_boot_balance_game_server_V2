package balance_game_v2.api.v2.user.http.res

import balance_game_v2.domain.user.dto.PageUserNotificationDTO

data class PageUserNotificationResponseDTO(
    val notifications: PageUserNotificationDTO
)
