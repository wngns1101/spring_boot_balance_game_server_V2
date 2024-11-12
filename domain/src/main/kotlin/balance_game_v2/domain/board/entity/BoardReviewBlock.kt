package balance_game_v2.domain.board.entity

import balance_game_v2.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_review_block")
class BoardReviewBlock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardReviewBlockId: Long? = null,
    val userId: Long,
    val boardId: Long,
    val boardReviewId: Long,
    val reason: String,
) : BaseEntity()
