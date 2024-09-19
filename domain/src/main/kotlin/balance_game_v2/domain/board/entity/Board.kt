package balance_game_v2.domain.board.entity

import balance_game_v2.domain.BaseEntity
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
    val themeId: Long,
    var title: String,
    var introduce: String,
    var likeCount: Int = 0,
    var dislikeCount: Int = 0,
    var viewCount: Int = 0,
) : BaseEntity()
