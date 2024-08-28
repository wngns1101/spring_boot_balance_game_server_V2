package domain.auth.entity

import domain.BaseEntity
import domain.auth.model.AuthGroup
import domain.auth.model.AuthStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "auth")
class Auth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val authId: Long? = null,
    @Enumerated(value = EnumType.STRING)
    val authGroup: AuthGroup,
    val accountName: String,
    var password: String,
    var refreshToken: String? = null,
    @Enumerated(value = EnumType.STRING)
    var status: AuthStatus,
) : BaseEntity()
