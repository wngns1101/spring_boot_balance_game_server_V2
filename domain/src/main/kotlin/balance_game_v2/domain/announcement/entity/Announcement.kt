package balance_game_v2.domain.announcement.entity

import balance_game_v2.domain.BaseEntity
import balance_game_v2.domain.announcement.model.SearchCondition
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "announcement")
class Announcement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val announcementId: Long? = null,
    @Enumerated(EnumType.STRING)
    var type: SearchCondition,
    var title: String,
    var content: String,
    var viewCount: Int = 0,
) : BaseEntity()
