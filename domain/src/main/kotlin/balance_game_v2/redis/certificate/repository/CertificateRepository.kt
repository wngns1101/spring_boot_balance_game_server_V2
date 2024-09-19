package balance_game_v2.redis.certificate.repository

import balance_game_v2.redis.certificate.entity.Certificate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificateRepository : CrudRepository<Certificate, String> {
    override fun findAll(): List<Certificate>
}
