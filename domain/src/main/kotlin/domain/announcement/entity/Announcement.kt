package domain.announcement.entity

import domain.BaseEntity
import domain.announcement.model.SearchCondition
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
    val type: SearchCondition,
    var title: String,
    var content: String,
    var viewCount: Int = 0,
) : BaseEntity()
