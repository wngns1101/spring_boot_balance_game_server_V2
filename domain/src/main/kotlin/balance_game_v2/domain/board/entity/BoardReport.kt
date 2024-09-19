package balance_game_v2.domain.board.entity

import balance_game_v2.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_report")
class BoardReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardReportId: Long? = null,
    val boardId: Long,
    val userId: Long,
    val content: String,
) : BaseEntity()
