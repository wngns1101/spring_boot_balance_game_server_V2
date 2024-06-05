package domain.board.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "board_result")
class BoardResult (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardResultId: Long? = null,
    val boardId: Long,
    val boardContentId: Long,
    val userId: Long,
): BaseEntity()
