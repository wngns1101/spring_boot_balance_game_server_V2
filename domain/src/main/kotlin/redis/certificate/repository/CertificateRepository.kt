package redis.certificate.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import redis.certificate.entity.Certificate

@Repository
interface CertificateRepository : CrudRepository<Certificate, String> {
    override fun findAll(): List<Certificate>
}
