package domain.user.repository

import domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByAccountNameAndDeletedAtIsNull(id: String): User?

    fun existsUserByNickname(nickname: String): Boolean
}
