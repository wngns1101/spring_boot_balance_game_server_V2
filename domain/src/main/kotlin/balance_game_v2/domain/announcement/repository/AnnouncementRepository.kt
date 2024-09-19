package balance_game_v2.domain.announcement.repository

import balance_game_v2.domain.announcement.entity.Announcement
import balance_game_v2.domain.announcement.model.SearchCondition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnnouncementRepository : JpaRepository<Announcement, Long> {
    fun findAnnouncementByType(type: SearchCondition): List<Announcement>
}
