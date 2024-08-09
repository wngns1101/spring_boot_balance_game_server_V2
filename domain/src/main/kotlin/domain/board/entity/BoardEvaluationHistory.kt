package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_evaluation_history")
class BoardEvaluationHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardEvaluationHistoryId: Long? = null,
    val boardId: Long,
    val userId: Long,
    val isLike: Boolean,
    val isDislike: Boolean,
) : BaseEntity()
