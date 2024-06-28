package domain.user.repository

import domain.user.entity.AdjectiveName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdjectiveNameRepository : JpaRepository<AdjectiveName, String>
