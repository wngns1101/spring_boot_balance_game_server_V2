package domain.auth.repository

import domain.auth.entity.Auth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository : JpaRepository<Auth, Long> {
    fun existsByEmail(email: String): Boolean

    fun findByEmailAndDeletedAtIsNull(email: String): Auth?

    fun findByEmail(email: String): Auth?
}
