package balance_game_v2.domain.announcement.repository.querydsl

import balance_game_v2.domain.announcement.entity.Announcement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AnnouncementQueryDslRepository {
    fun search(query: String?, pageable: Pageable): Page<Announcement>
}
