package balance_game_v2.domain.board.entity

import balance_game_v2.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_result")
class BoardResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardResultId: Long? = null,
    val boardContentId: Long,
    val userId: Long,
    val boardId: Long,
    val boardContentItemId: Long,
) : BaseEntity()
