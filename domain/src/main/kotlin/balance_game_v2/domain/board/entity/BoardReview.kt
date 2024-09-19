package balance_game_v2.domain.board.entity

import balance_game_v2.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_review")
class BoardReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardReviewId: Long? = null,
    val boardId: Long,
    val userId: Long,
    var title: String,
    var comment: String,
    var isLike: Boolean,
    var isDislike: Boolean,
) : BaseEntity()
