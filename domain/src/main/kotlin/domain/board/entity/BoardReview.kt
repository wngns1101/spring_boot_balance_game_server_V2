package domain.board.entity

import domain.BaseEntity
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
    var comment: String,
) : BaseEntity()
