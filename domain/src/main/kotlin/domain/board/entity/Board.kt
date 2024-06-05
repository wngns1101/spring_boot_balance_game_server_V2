package domain.board.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "board")
class Board (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardId: Long? = null,
    val userId: Long,
    var title: String,
    var content: String,
    var heartCount: Int = 0,
    var viewCount: Int = 0,
): BaseEntity()