package balance_game_v2.domain.theme.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "theme")
class Theme(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val themeId: Long? = null,
    var theme: String,
    var iconUrl: String?,
)
