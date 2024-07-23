package domain.user.entity

import domain.BaseEntity
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
    val realName: String,
    val accountName: String,
    val birth: String,
    var email: String,
    val invitationCode: String,
    var pushToken: String? = null,
    var profileUrl: String? = null,
) : BaseEntity()
