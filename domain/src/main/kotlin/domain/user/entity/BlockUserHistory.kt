package domain.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "block_user_history")
class BlockUserHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val blockUserHistoryId: Long? = null,
    val adminId: Long,
    val userId: Long,
    val reason: String,
)
