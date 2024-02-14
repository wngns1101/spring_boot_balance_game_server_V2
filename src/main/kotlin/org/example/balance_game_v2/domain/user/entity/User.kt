package org.example.balance_game_v2.domain.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.example.balance_game_v2.domain.BaseEntity

@Entity
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null,
    val email: String,
    val nickname: String,
    val realName: String,
    val birth: String,
): BaseEntity()