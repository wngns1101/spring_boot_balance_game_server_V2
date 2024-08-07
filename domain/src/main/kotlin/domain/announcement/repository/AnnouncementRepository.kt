package domain.announcement.repository

import domain.announcement.entity.Announcement
import domain.announcement.model.SearchCondition
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnnouncementRepository : JpaRepository<Announcement, Long> {
    fun findAnnouncementByType(type: SearchCondition): List<Announcement>
}
