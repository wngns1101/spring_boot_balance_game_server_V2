package domain.notification.entity

import domain.BaseEntity
import domain.notification.model.NotificationStatus
import domain.notification.model.NotificationType
import jakarta.persistence.*

@Table(name = "notification")
@Entity
class Notification (
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
): BaseEntity()