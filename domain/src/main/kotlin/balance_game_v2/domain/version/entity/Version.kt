package balance_game_v2.domain.version.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "version")
@Entity
class Version(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val versionId: Long? = null,
    var currentVersion: String,
    var minimumVersion: String,
    var preferVersion: String,
)
