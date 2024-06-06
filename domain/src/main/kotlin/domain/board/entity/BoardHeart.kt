package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_heart")
class BoardHeart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardHeartId: Long? = null,
    val boardId: Long,
    val userId: Long,
) : BaseEntity()
