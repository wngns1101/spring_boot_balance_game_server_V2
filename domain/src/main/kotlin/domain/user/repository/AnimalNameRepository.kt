package domain.user.repository

import domain.user.entity.AnimalName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimalNameRepository : JpaRepository<AnimalName, String>
