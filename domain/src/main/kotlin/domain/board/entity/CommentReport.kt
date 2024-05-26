package domain.board.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "comment_report")
class CommentReport (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val commentReportId: Long = 0,
    val commentId: Long,
    val userId: Long,
): BaseEntity()