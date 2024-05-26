package domain.board.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "board_comment")
class BoardComment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardCommentId: Long? = null,
    val boardId: Long,
    val userId: Long,
    val parentUserId: Long,
    val comment: String,
): BaseEntity()