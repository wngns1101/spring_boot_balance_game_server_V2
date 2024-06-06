package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "comment_report")
class CommentReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentReportId: Long = 0,
    val commentId: Long,
    val userId: Long,
) : BaseEntity()
