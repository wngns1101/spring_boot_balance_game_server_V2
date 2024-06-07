package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_comment_report")
class BoardCommentReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardCommentReportId: Long = 0,
    val boardCommentId: Long,
    val userId: Long,
) : BaseEntity()
