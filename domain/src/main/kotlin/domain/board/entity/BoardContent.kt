package domain.board.entity

import jakarta.persistence.*
import domain.BaseEntity

@Entity
@Table(name = "board_content")
class BoardContent (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardContentId: Long? = null,
    val boardId: Long,
    val title: String,
    val photoUrl: String,
): BaseEntity()