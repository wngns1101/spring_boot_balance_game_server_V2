package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_review_report")
class BoardReviewReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardReviewReportId: Long = 0,
    val boardReviewId: Long,
    val userId: Long,
    val content: String,
) : BaseEntity()
