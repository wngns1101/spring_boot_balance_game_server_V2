package balance_game_v2.domain.board.dto

import balance_game_v2.domain.board.entity.Board
import java.time.LocalDateTime

data class BoardDTO(
    val boardId: Long,
    val themeId: Long,
    val title: String,
    val introduce: String,
    val userId: Long,
    val viewCount: Int,
    val likeCount: Int,
    val dislikeCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
)

fun Board.toDTO() = BoardDTO(
    boardId = boardId!!,
    themeId = themeId,
    title = title,
    introduce = introduce,
    userId = userId,
    viewCount = viewCount,
    likeCount = likeCount,
    dislikeCount = dislikeCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
)
