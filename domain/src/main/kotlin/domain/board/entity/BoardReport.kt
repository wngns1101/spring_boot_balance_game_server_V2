package domain.board.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "board_report")
class BoardReport (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardReportId: Long? = null,
    val boardId: Long,
    val userId: Long,
): BaseEntity()