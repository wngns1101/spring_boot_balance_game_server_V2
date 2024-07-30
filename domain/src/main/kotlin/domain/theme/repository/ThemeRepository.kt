package domain.theme.repository

import domain.theme.entity.Theme
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ThemeRepository : JpaRepository<Theme, Long>
