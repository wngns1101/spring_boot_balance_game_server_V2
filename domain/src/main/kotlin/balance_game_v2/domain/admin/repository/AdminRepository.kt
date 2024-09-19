package balance_game_v2.domain.admin.repository

import balance_game_v2.domain.admin.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : JpaRepository<Admin, Long>
