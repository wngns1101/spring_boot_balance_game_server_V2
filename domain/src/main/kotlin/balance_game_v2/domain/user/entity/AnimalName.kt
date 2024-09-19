package balance_game_v2.domain.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "animal_name")
data class AnimalName(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val animalNameId: Long? = null,
    val name: String,
)
