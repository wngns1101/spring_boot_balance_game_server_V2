package domain.user.entity

import domain.user.model.UserNotificationType
import jakarta.persistence.*

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