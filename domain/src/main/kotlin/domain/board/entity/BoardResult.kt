package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_result")
class BoardResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardResultId: Long? = null,
    val boardId: Long,
    val boardContentId: Long,
    val userId: Long,
) : BaseEntity()
