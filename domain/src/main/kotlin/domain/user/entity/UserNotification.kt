package domain.user.entity

import domain.BaseEntity
import domain.user.model.UserNotificationStatus
import domain.user.model.UserNotificationType
import jakarta.persistence.*


@Table(name = "user_notification")
@Entity
class UserNotification (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userNotificationId: Long? = null,
    val userId: Long,
    @Enumerated(EnumType.STRING)
    val type: UserNotificationType,
    @Enumerated(EnumType.STRING)
    val status: UserNotificationStatus,
): BaseEntity()