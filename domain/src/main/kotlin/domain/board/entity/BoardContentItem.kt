package domain.board.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "board_content_item")
class BoardContentItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val boardContentItemId: Long? = null,
    val boardContentId: Long,
    var item: String,
)
