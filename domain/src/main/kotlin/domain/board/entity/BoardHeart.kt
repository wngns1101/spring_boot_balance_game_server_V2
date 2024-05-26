package domain.board.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "board_heart")
class BoardHeart (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardHeartId: Long? = null,
    val boardId: Long,
    val userId: Long,
):BaseEntity()