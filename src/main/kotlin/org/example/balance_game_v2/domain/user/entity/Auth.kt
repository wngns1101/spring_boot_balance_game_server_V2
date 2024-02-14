package org.example.balance_game_v2.domain.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.example.balance_game_v2.domain.BaseEntity

@Entity
class Auth (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val authId: Long? = null,
    val email: String,
    val password: String,
):BaseEntity()