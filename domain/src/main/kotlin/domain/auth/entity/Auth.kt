package domain.auth.entity

import jakarta.persistence.*
import domain.BaseEntity
import domain.auth.model.AuthGroup

@Entity
@Table(name = "auth")
class Auth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val authId: Long? = null,
    @Enumerated(value = EnumType.STRING)
    val authGroup: AuthGroup,
    val email: String,
    var password: String,
) : BaseEntity()
