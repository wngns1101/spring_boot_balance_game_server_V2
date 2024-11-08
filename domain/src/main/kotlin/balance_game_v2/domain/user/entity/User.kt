package balance_game_v2.domain.user.entity

import balance_game_v2.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    var nickname: String,
    var realName: String,
    var accountName: String,
    var email: String,
    val invitationCode: String,
    var pushToken: String? = null,
    var profileUrl: String? = null,
    var deletedReason: String? = null,
) : BaseEntity()
