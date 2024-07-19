package domain.board.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_keyword")
class BoardKeyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardKeywordId: Long? = null,
    val boardId: Long,
    val keyword: String,
)
