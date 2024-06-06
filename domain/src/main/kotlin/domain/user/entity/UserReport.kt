package domain.user.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "user_report")
@Entity
class UserReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userReport: Long? = null,
    val userId: Long,
    val targetUserId: Long,
) : BaseEntity()
