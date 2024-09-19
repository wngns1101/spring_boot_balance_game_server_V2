package balance_game_v2.domain.user.entity

import balance_game_v2.domain.user.model.UserNotificationType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_notification")
class UserNotification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userNotificationId: Long? = null,
    val userId: Long,
    @Enumerated(EnumType.STRING)
    val type: UserNotificationType,
    var status: Boolean,
)
