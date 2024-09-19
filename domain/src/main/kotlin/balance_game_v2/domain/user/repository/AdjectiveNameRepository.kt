package balance_game_v2.domain.user.repository

import balance_game_v2.domain.user.entity.AdjectiveName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdjectiveNameRepository : JpaRepository<AdjectiveName, String>
