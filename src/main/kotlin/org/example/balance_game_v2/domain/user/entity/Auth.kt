package org.example.balance_game_v2.domain.user.entity

import org.example.balance_game_v2.domain.BaseEntity

class Auth (
    val authId: Long,
    val email: String,
    val password: String,
):BaseEntity()