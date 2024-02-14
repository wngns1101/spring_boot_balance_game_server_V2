package org.example.balance_game_v2.domain.user.repository

import org.example.balance_game_v2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmailAndDeletedAtIsNull(email: String): User?
}
