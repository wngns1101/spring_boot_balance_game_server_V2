package balance_game_v2.api.v1.user.http.res

import domain.user.dto.PageUserNotificationDTO

data class PageUserNotificationResponseDTO(
    val notifications: PageUserNotificationDTO
)
