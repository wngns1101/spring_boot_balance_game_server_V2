package domain.auth.repository

import domain.auth.entity.Auth
import domain.auth.model.AuthStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository : JpaRepository<Auth, Long> {
    fun existsByAccountName(id: String): Boolean

    fun findByAccountNameAndDeletedAtIsNull(id: String): Auth?

    fun findByAccountName(accountName: String): Auth?

    fun findByStatus(status: AuthStatus): List<Auth>

    fun findAllByAccountNameIn(accountNames: List<String>): List<Auth>
}
