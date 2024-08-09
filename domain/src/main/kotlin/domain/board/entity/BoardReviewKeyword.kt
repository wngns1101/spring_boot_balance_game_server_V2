package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_review_keyword")
class BoardReviewKeyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardReviewKeywordId: Long? = null,
    val boardReviewId: Long,
    val userId: Long,
    val keyword: String,
) : BaseEntity()
