package balance_game_v2.api.v1.user.http.res

import domain.user.dto.UserNotificationDTO

data class ListUserNotificationResponseDTO(
    val notifications: List<UserNotificationDTO>
)
