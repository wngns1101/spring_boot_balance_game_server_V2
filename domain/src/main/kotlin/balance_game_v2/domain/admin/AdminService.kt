package balance_game_v2.domain.admin

import balance_game_v2.domain.admin.repository.AdminRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val adminRepository: AdminRepository,
) {
    fun signIn() {
    }
}
