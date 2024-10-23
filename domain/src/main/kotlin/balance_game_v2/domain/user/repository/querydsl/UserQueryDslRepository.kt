package balance_game_v2.domain.user.repository.querydsl

import balance_game_v2.domain.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserQueryDslRepository {
    fun search(query: String?, pageable: Pageable): Page<User>
}
