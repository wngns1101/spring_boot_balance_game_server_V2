package balance_game_v2.domain.user.repository

import balance_game_v2.domain.user.entity.User
import balance_game_v2.domain.user.repository.querydsl.UserQueryDslRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, UserQueryDslRepository {
    fun findByAccountNameAndDeletedAtIsNull(id: String): User?
    fun existsUserByNickname(nickname: String): Boolean
    fun findAllByUserIdIn(userIds: List<Long>): List<User>
    fun findAllByAccountNameIn(accountNames: List<String>): List<User>
    fun deleteByUserId(userId: Long)
    fun findByEmailAndDeletedAtIsNull(email: String): User?
    fun existsUserByEmail(email: String): Boolean
}
