package domain.theme.entity

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
    val theme: String,
    val iconUrl: String,
)
