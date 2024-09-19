package balance_game_v2.domain.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "adjective_name")
class AdjectiveName(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var adjectiveNameId: Long? = null,
    val name: String? = null,
)
