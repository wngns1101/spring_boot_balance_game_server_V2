package domain.admin.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "admin")
class Admin(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val adminId: Long? = null,
    val email: String,
    val password: String,
    val realName: String,
) : BaseEntity()
