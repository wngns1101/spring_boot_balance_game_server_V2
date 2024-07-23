package domain.auth.repository

import domain.auth.entity.Auth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository : JpaRepository<Auth, Long> {
    fun existsByAccountName(id: String): Boolean

    fun findByAccountNameAndDeletedAtIsNull(id: String): Auth?

    fun findByAccountName(id: String): Auth?
}
