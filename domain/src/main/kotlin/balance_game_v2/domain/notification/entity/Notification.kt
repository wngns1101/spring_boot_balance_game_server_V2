package balance_game_v2.domain.notification.entity

import balance_game_v2.domain.BaseEntity
import balance_game_v2.domain.notification.model.NotificationStatus
import balance_game_v2.domain.notification.model.NotificationType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "notification")
@Entity
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userNotificationId: Long? = null,
    val userId: Long,
    val title: String,
    val body: String,
    val imageUrl: String,
    val link: String,
    @Enumerated(EnumType.STRING)
    val type: NotificationType,
    @Enumerated(EnumType.STRING)
    val status: NotificationStatus,
    var isRead: Boolean = false,
) : BaseEntity()
