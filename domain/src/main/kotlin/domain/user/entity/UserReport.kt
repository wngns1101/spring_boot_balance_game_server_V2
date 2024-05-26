package domain.user.entity

import domain.BaseEntity
import jakarta.persistence.*

@Table(name = "user_report")
@Entity
class UserReport (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userReport: Long? = null,
    val userId: Long,
    val targetUserId: Long,
): BaseEntity()