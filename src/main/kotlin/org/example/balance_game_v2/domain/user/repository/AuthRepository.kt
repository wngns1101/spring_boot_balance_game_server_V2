package org.example.balance_game_v2.domain.user.repository

import org.example.balance_game_v2.domain.user.entity.Auth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository: JpaRepository<Auth, Long> {
    fun existsByEmail(email: String): Boolean
}