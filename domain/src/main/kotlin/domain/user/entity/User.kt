package domain.user.entity

import domain.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    var nickname: String,
    val realName: String,
    val email: String,
    val birth: String,
    var phoneNumber: String,
    val invitationCode: String,
    var pushToken: String? = null,
) : BaseEntity()
