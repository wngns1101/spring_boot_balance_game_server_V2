package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardId: Long? = null,
    val userId: Long,
    var title: String,
    var content: String,
    var heartCount: Int = 0,
    var viewCount: Int = 0,
) : BaseEntity()
