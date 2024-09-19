package balance_game_v2.api.v2.user.http.res

data class ListUserNotificationResponseDTO(
    val notifications: List<balance_game_v2.domain.user.dto.UserNotificationDTO>
)
