package domain.board.entity

import domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_content")
class BoardContent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardContentId: Long? = null,
    val boardId: Long,
    val title: String,
    val photoUrl: String,
) : BaseEntity()
